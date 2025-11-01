package com.example.benedictcumberbatch.di

import com.example.benedictcumberbatch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val newRequest = original.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_BEARER}")
            .build()

        return chain.proceed(newRequest)
    }
}