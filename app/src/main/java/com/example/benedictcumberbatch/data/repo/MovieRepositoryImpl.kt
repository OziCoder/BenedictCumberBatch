package com.example.benedictcumberbatch.data.repo

import com.example.benedictcumberbatch.data.remote.TmdbApi
import com.example.benedictcumberbatch.data.remote.toDomain
import com.example.benedictcumberbatch.domain.model.Movie

class MovieRepositoryImpl(private val api: TmdbApi) : MovieRepository {
    override suspend fun getMovies(): Result<List<Movie>> = runCatching {
        api.getMovies().results.map { it.toDomain() }
    }
}