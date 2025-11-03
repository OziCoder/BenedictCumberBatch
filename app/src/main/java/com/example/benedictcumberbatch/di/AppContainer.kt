package com.example.benedictcumberbatch.di

import com.example.benedictcumberbatch.BuildConfig
import com.example.benedictcumberbatch.data.remote.TmdbApi
import com.example.benedictcumberbatch.data.repo.MovieRepository
import com.example.benedictcumberbatch.data.repo.MovieRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val tmdbApi: TmdbApi
    val movieRepo: MovieRepository
}

class DefaultAppContainer() : AppContainer {

    private val client by lazy {
        OkHttpClient.Builder()
            // Add API key interceptor first
            .addInterceptor(ApiInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            )
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    override val tmdbApi: TmdbApi by lazy { retrofit.create(TmdbApi::class.java) }
    override val movieRepo: MovieRepository by lazy { MovieRepositoryImpl(tmdbApi) }
}