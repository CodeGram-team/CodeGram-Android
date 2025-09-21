package com.code.gram.data.datasource

import android.content.Context
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

// 구글에서 idToken을 받아오기 위함
interface GoogleAuthDataSource {
    suspend fun signIn(context: Context): Result<GoogleIdTokenCredential>
}

