package com.code.gram.data.di

import com.code.gram.data.datasource.TokenManager
import com.code.gram.data.dto.request.RefreshRequest
import com.code.gram.data.service.LoginService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    /**
     * 순환 참조 방지를 위해 @RefreshService Qualifier가 붙은,
     * AuthInterceptor가 없는 클라이언트를 사용하는 AuthService를 주입받습니다.
     */
    @param:RefreshService private val authService: LoginService,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // 1. 원본 요청을 가져옵니다.
        val originalRequest = chain.request()

        // 2. 토큰 재발급 요청인 경우, 헤더를 추가하지 않고 바로 실행합니다.
        if (originalRequest.url.encodedPath.contains("api/v1/refresh")) {
            return chain.proceed(originalRequest)
        }

        // 3. 현재 액세스 토큰을 가져와 헤더에 추가합니다. (runBlocking 사용)
        val accessToken = runBlocking { tokenManager.getAccessToken().firstOrNull() }
        val requestWithHeader = if (accessToken != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else {
            originalRequest
        }

        // 4. 헤더가 추가된 요청을 보냅니다.
        var response = chain.proceed(requestWithHeader)

        // 5. 401 Unauthorized 오류가 발생한 경우 (액세스 토큰 만료)
        if (response.code == 401) {
            Timber.w("AuthInterceptor: 401 Unauthorized. Access token expired. Trying to refresh...")

            // 6. 여러 요청이 동시에 401을 받아도 재발급은 한 번만 실행되도록 동기화합니다.
            synchronized(this) {
                // 7. 토큰 재발급을 시도하기 직전에, 다른 스레드가 이미 재발급에 성공했는지 확인합니다.
                val newAccessToken = runBlocking { tokenManager.getAccessToken().firstOrNull() }

                if (accessToken != newAccessToken) {
                    // 7-1. 토큰이 이미 갱신됨: 401 응답을 닫고, 새 토큰으로 요청을 다시 만듭니다.
                    response.close()
                    val newRequest = originalRequest.withNewToken(newAccessToken)
                    Timber.i("AuthInterceptor: Token already refreshed by another thread. Retrying request.")
                    return chain.proceed(newRequest)
                }

                // 8. 토큰 갱신이 필요함: 리프레시 토큰을 가져옵니다.
                val refreshToken = runBlocking { tokenManager.getRefreshToken().firstOrNull() }
                if (refreshToken == null) {
                    // 8-1. 리프레시 토큰 없음: 토큰을 지우고, 401 응답을 반환 (재로그인 유도)
                    Timber.e("AuthInterceptor: No refresh token found. Clearing tokens and logging out.")
                    runBlocking { tokenManager.clearAllTokens() }
                    return response // 원래 401 응답 반환
                }

                // 9. 리프레시 토큰으로 새 토큰을 요청합니다. (runBlocking 사용)
                Timber.d("AuthInterceptor: Refreshing token...")
                val refreshResult = runBlocking {
                    try {
                        authService.refreshToken(RefreshRequest(refreshToken))
                    } catch (e: Exception) {
                        Timber.e(e, "AuthInterceptor: Token refresh API call failed.")
                        null // API 호출 실패
                    }
                }

                if (refreshResult != null && refreshResult.isSuccessful) {
                    // 10. 재발급 성공
                    val authResponse = refreshResult.body()
                    if (authResponse != null) {
                        Timber.i("AuthInterceptor: Token refreshed successfully. Saving new tokens.")
                        // 10-1. 새 토큰 저장
                        runBlocking {
                            tokenManager.saveAccessToken(authResponse.accessToken, authResponse.expiresTime ?: 2000000000)
                            tokenManager.saveRefreshToken(authResponse.refreshToken, authResponse.expiresTime ?: 2000000000)
                        }
                        // 10-2. 원래 401 응답 닫기
                        response.close()
                        // 10-3. 새 토큰으로 요청 재시도
                        val newRequest = originalRequest.withNewToken(authResponse.accessToken)
                        return chain.proceed(newRequest)
                    }
                }

                // 11. 재발급 실패 (리프레시 토큰 만료 등)
                Timber.e("AuthInterceptor: Token refresh failed. (Code: ${refreshResult?.code()}). Clearing tokens and logging out.")
                // 11-1. 모든 토큰 삭제
                runBlocking { tokenManager.clearAllTokens() }
                // 11-2. 원래 401 응답 반환 (재로그인 유도)
                return response
            }
        }

        // 12. 401이 아닌 경우, 원래 응답 반환
        return response
    }

    private fun Request.withNewToken(newAccessToken: String?): Request {
        if (newAccessToken == null) return this
        return this.newBuilder()
            .removeHeader("Authorization") // 기존 헤더 제거
            .addHeader("Authorization", "Bearer $newAccessToken") // 새 헤더 추가
            .build()
    }
}