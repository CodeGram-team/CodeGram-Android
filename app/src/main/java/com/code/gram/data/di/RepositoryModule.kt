package com.code.gram.data.di

import com.code.gram.data.repositoryimpl.AuthRepositoryImpl
import com.code.gram.data.repositoryimpl.ChallengeRepositoryImpl
import com.code.gram.data.repositoryimpl.HomeRepositoryImpl
import com.code.gram.data.repositoryimpl.ProfileRepositoryImpl
import com.code.gram.data.repositoryimpl.SearchRepositoryImpl
import com.code.gram.data.repositoryimpl.WebSocketRepositoryImpl
import com.code.gram.domain.repository.AuthRepository
import com.code.gram.domain.repository.ChallengeRepository
import com.code.gram.domain.repository.HomeRepository
import com.code.gram.domain.repository.ProfileRepository
import com.code.gram.domain.repository.SearchRepository
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

    @Binds
    @Singleton
    abstract fun bindChallengeRepository(
        challengeRepositoryImpl: ChallengeRepositoryImpl
    ): ChallengeRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository
}