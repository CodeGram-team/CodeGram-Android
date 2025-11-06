package com.code.gram.data.di


import javax.inject.Qualifier

/**
 * 기본 OkHttpClient (AuthInterceptor 포함)를 주입하기 위한 Qualifier
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainClient

/**
 * 토큰 재발급 전용 OkHttpClient (AuthInterceptor 없음)를 주입하기 위한 Qualifier
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshClient

/**
 * 토큰 재발급 전용 AuthService (RefreshClient 사용)를 주입하기 위한 Qualifier
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshService
