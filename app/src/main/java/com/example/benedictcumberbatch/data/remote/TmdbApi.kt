package com.example.benedictcumberbatch.data.remote
import retrofit2.http.GET

interface TmdbApi {
    @GET("discover/movie")
    suspend fun getMovies(): Movies
}