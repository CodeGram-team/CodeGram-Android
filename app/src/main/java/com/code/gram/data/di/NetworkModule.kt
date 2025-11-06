package com.code.gram.data.di

import com.code.gram.BuildConfig
import com.code.gram.data.datasource.websocket.WebSocketManager
import com.code.gram.data.service.LoginService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWebSocketManager(): WebSocketManager = WebSocketManager()

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        return json.asConverterFactory("application/json".toMediaType())
    }

    // --- (수정됨) 순환 참조 방지를 위한 OkHttpClient 및 Retrofit/AuthService 제공 ---

    /**
     * (추가됨) 토큰 재발급 전용 OkHttpClient (Interceptor 없음)
     */
    @Provides
    @Singleton
    @RefreshClient
    fun providesRefreshOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor) // 인증 인터셉터(AuthInterceptor)를 추가하지 않음
        .build()

    /**
     * (수정됨) 앱 기본 OkHttpClient (Interceptor 포함)
     */
    @Provides
    @Singleton
    @MainClient
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor, // AuthInterceptor 주입
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor) // 인증 인터셉터 추가
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * (수정됨) 앱 기본 Retrofit (MainClient 사용)
     */
    @Provides
    @Singleton
    fun providesRetrofit(
        @MainClient client: OkHttpClient, // @MainClient 주입
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(converterFactory)
        .client(client)
        .build()

    /**
     * (추가됨) 토큰 재발급 전용 AuthService (RefreshClient 사용)
     * AuthInterceptor에서만 주입받아 사용합니다.
     */
    @Provides
    @Singleton
    @RefreshService
    fun providesRefreshAuthService(
        @RefreshClient client: OkHttpClient, // @RefreshClient 주입
        converterFactory: Converter.Factory
    ): LoginService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(converterFactory)
        .client(client) // AuthInterceptor가 없는 클라이언트 사용
        .build()
        .create(LoginService::class.java)
}