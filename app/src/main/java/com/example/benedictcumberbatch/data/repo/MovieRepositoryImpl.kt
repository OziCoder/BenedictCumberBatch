package com.example.benedictcumberbatch.data.repo

import com.example.benedictcumberbatch.data.remote.TmdbApi
import com.example.benedictcumberbatch.data.remote.toDomain
import com.example.benedictcumberbatch.domain.model.Movie
import com.example.benedictcumberbatch.domain.model.MovieDetail

class MovieRepositoryImpl(private val api: TmdbApi) : MovieRepository {
    override suspend fun getMovies(): Result<List<Movie>> = runCatching {
        api.getMovies().results.map { it.toDomain() }
    }
    override suspend fun movieDetail(id: Long): Result<MovieDetail> = runCatching {
        api.movieDetail(id).toDomain()
    }
    override suspend fun similarMovies(id: Long): Result<List<Movie>> = runCatching {
        api.similarMovies(id).results.map { it.toDomain() }
    }
}