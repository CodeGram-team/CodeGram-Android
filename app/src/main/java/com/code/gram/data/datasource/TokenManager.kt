package com.code.gram.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    // DataStore에 토큰을 저장할 때 사용할 키
    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    private val signupTokenKey = stringPreferencesKey("signup_token")

    private val accessTokenExpirationKey = longPreferencesKey("access_token_expiration")
    private val refreshTokenExpirationKey = longPreferencesKey("refresh_token_expiration")


    /**
     * DataStore에서 Access Token을 Flow 형태로 가져옵니다.
     */
    fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[accessTokenKey]
        }
    }

    /**
     * DataStore에 Access Token을 저장합니다.
     */
    suspend fun saveAccessToken(token: String, expirationTime: Long) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
            preferences[accessTokenExpirationKey] = expirationTime * 1000
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[refreshTokenKey]
        }
    }

    suspend fun saveRefreshToken(token: String, expirationTime: Long) {
        dataStore.edit { preferences ->
            preferences[refreshTokenKey] = token
            preferences[refreshTokenExpirationKey] = expirationTime * 1000
        }
    }

    suspend fun saveSignUpToken(token: String) {
        dataStore.edit { preferences ->
            preferences[signupTokenKey] = token
        }
    }

    fun getSignUpToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[signupTokenKey]
        }
    }

    fun isAccessTokenExpired(): Flow<Boolean> =
        dataStore.data.map { prefs ->
            val expireAt = prefs[accessTokenExpirationKey] ?: 0L
            System.currentTimeMillis() > expireAt
        }

    fun isRefreshTokenExpired(): Flow<Boolean> =
        dataStore.data.map { prefs ->
            val expireAt = prefs[refreshTokenExpirationKey] ?: 0L
            System.currentTimeMillis() > expireAt
        }

    suspend fun clearAllTokens() {
        dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
            preferences.remove(refreshTokenKey)
            preferences.remove(signupTokenKey)
            preferences.remove(accessTokenExpirationKey)
            preferences.remove(refreshTokenExpirationKey)
        }
    }
}