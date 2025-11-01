package com.example.benedictcumberbatch.data.remote

import com.example.benedictcumberbatch.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("discover/movie")
    suspend fun getMovies(): Movies

    @GET("movie/{id}")
    suspend fun movieDetail(
        @Path("id") id: Long,
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieDetailDto

    @GET("movie/{id}/similar")
    suspend fun similarMovies(
        @Path("id") id: Long,
        @Query("page") page: Int = 1,
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Movies
}