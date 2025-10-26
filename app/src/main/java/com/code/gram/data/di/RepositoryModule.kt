package com.code.gram.data.di

import com.code.gram.data.repositoryimpl.AuthRepositoryImpl
import com.code.gram.data.repositoryimpl.HomeRepositoryImpl
import com.code.gram.data.repositoryimpl.WebSocketRepositoryImpl
import com.code.gram.domain.repository.AuthRepository
import com.code.gram.domain.repository.HomeRepository
import com.code.gram.domain.repository.WebSocketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindWebSocketRepository(
        webSocketRepositoryImpl: WebSocketRepositoryImpl
    ): WebSocketRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

}