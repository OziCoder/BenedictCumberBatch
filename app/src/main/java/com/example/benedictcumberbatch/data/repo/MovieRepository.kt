package com.example.benedictcumberbatch.data.repo

import com.example.benedictcumberbatch.domain.model.Movie
import com.example.benedictcumberbatch.domain.model.MovieDetail

interface MovieRepository {
    suspend fun getMovies(): Result<List<Movie>>
    suspend fun movieDetail(id: Long): Result<MovieDetail>
    suspend fun similarMovies(id: Long): Result<List<Movie>>
}