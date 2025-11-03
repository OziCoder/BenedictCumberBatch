package com.example.benedictcumberbatch.data.repo

import com.example.benedictcumberbatch.data.remote.Movies
import com.example.benedictcumberbatch.data.remote.Results
import com.example.benedictcumberbatch.data.remote.TmdbApi
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {

    @MockK
    lateinit var api: TmdbApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getMovies_maps_success_response() = runTest {
        val results = listOf(
            Results(id = 1, title = "Doctor Strange", posterPath = "/p", overview = "O")
        )
        coEvery { api.getMovies() } returns Movies(page = 1, results = results)

        val repo: MovieRepository = MovieRepositoryImpl(api)
        val res = repo.getMovies()

        assertTrue(res.isSuccess)
        val list = res.getOrNull()!!
        assertEquals(1, list.size)
        assertEquals("Doctor Strange", list.first().title)
    }

    @Test
    fun getMovies_propagates_failure() = runTest {
        coEvery { api.getMovies() } throws RuntimeException("network down")

        val repo: MovieRepository = MovieRepositoryImpl(api)
        val res = repo.getMovies()

        assertTrue(res.isFailure)
        assertEquals("network down", res.exceptionOrNull()!!.message)
    }
}