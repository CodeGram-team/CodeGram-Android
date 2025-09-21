package com.code.gram.data.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.code.gram.data.datasource.AuthRemoteDataSource
import com.code.gram.data.datasource.GoogleAuthDataSource
import com.code.gram.data.datasourceimpl.AuthRemoteDataSourceImpl
import com.code.gram.data.datasourceimpl.GoogleAuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindGoogleAuthDataSource(
        googleAuthDataSourceImpl: GoogleAuthDataSourceImpl
    ): GoogleAuthDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    internal companion object {
        @Provides
        @Singleton
        fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
            return CredentialManager.create(context)
        }
    }
}