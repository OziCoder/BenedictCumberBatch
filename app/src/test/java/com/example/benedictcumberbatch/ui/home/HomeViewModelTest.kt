package com.example.benedictcumberbatch.ui.home

import com.example.benedictcumberbatch.data.repo.MovieRepository
import com.example.benedictcumberbatch.domain.model.Movie
import com.example.benedictcumberbatch.testing.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val main = MainDispatcherRule()

    @MockK
    lateinit var repo: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun load_success_emits_items_and_no_error() = runTest {
        val movies = listOf(Movie(1, "Doctor Strange", "/p", "Test overview"))
        coEvery { repo.getMovies() } returns Result.success(movies)

        val vm = HomeViewModel(repo)
        val state = vm.state.drop(1).first() // post-loading

        assertFalse(state.loading)
        assertNull(state.error)
        assertEquals(movies, state.items)
    }

    @Test
    fun load_error_emits_error_and_empty_items() = runTest {
        coEvery { repo.getMovies() } returns Result.failure(RuntimeException("Boom"))

        val vm = HomeViewModel(repo)
        val state = vm.state.drop(1).first()

        assertFalse(state.loading)
        assertTrue(state.items.isEmpty())
        assertEquals("Boom", state.error)
    }
}