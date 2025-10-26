package com.code.gram.data.di

import com.code.gram.data.service.HomeService
import com.code.gram.data.service.LoginService
import com.code.gram.data.service.WebSocketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun LoginService(retrofit: Retrofit): LoginService =
        retrofit.create()

    @Provides
    @Singleton
    fun webSocketService(retrofit: Retrofit): WebSocketService =
        retrofit.create()

    @Provides
    @Singleton
    fun homeService(retrofit: Retrofit): HomeService =
        retrofit.create()

}