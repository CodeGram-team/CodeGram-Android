package com.code.gram.data.datasourceimpl

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import android.os.Build
import androidx.annotation.RequiresApi
import com.code.gram.BuildConfig
import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.GoogleAuthDataSource
import javax.inject.Inject

class GoogleAuthDataSourceImpl @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Inject constructor(
    private val credentialManager: CredentialManager
) : GoogleAuthDataSource {
    override suspend fun signIn(context: Context): Result<GoogleIdTokenCredential> =
        suspendRunCatching {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val response = credentialManager.getCredential(context, request)

            GoogleIdTokenCredential.createFrom(response.credential.data)
        }
}