package com.example.benedictcumberbatch

import android.app.Application
import com.example.benedictcumberbatch.data.remote.Movies
import com.example.benedictcumberbatch.data.remote.TmdbApi
import com.example.benedictcumberbatch.data.repo.MovieRepository
import com.example.benedictcumberbatch.di.AppContainer

class TestApp: Application(), AppProvider {

    companion object {
        @Volatile lateinit var repo: MovieRepository
    }

    private val testContainer = object : AppContainer {
        override val tmdbApi: TmdbApi = object : TmdbApi {
            override suspend fun getMovies(): Movies = Movies()
        }
        override val movieRepo: MovieRepository
            get() = repo
    }

    override val container: AppContainer
        get() = testContainer
}