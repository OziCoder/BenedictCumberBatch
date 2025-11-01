package com.example.benedictcumberbatch.di

import com.example.benedictcumberbatch.BuildConfig
import com.example.benedictcumberbatch.data.remote.TmdbApi
import com.example.benedictcumberbatch.data.repo.MovieRepository
import com.example.benedictcumberbatch.data.repo.MovieRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val tmdbApi: TmdbApi
    val movieRepo: MovieRepository
}

class DefaultAppContainer(
    baseUrl: String = "https://api.themoviedb.org/3/"
) : AppContainer {

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val client by lazy {
        OkHttpClient.Builder()
            // Add API key interceptor first
            .addInterceptor(ApiInterceptor(BuildConfig.TMDB_API_KEY))
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            )
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    override val tmdbApi: TmdbApi by lazy { retrofit.create(TmdbApi::class.java) }
    override val movieRepo: MovieRepository by lazy { MovieRepositoryImpl(tmdbApi) }
}