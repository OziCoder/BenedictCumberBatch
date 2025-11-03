package com.example.benedictcumberbatch.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.benedictcumberbatch.R
import com.example.benedictcumberbatch.TestApp
import com.example.benedictcumberbatch.data.repo.MovieRepository
import com.example.benedictcumberbatch.domain.model.Movie
import com.example.benedictcumberbatch.testing.MainDispatcherRule
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper

@RunWith(AndroidJUnit4::class)
@Config(application = TestApp::class, sdk = [33])
class HomeFragmentTest {
    @get:Rule
    val main = MainDispatcherRule()

    @get:Rule
    val instant = InstantTaskExecutorRule()

    private lateinit var repo: MovieRepository

    private lateinit var activity: FragmentActivity

    @Before
    fun setUp() {
        repo = mockk(relaxed = true)
        TestApp.repo = repo

        // Host activity for the fragment
        activity = Robolectric.buildActivity(FragmentActivity::class.java)
            .setup() // create -> start -> resume
            .get()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    /**
     * Local "launchFragmentInContainer"-like helper that:
     *  - applies optional customMocks()
     *  - sets a custom FragmentFactory on the host Activity
     *  - instantiates + attaches the fragment synchronously
     */
    private fun launchFragmentInContainer(
        arguments: Bundle? = null,
        customMocks: () -> Unit = {}
    ): HomeFragment {
        // Allow test to modify mocks before creating the fragment/VM
        customMocks()

        // If you ever need to inject through FragmentFactory, do it here.
        // (For HomeFragment we just return a new instance.)
        val factory = object : FragmentFactory() {
            override fun instantiate(
                classLoader: ClassLoader,
                className: String
            ): Fragment {
                if (className == HomeFragment::class.java.name) {
                    return HomeFragment()
                }
                return super.instantiate(classLoader, className)
            }
        }

        activity.supportFragmentManager.fragmentFactory = factory

        val fragment = activity.supportFragmentManager.fragmentFactory
            .instantiate(activity.classLoader, HomeFragment::class.java.name) as HomeFragment

        fragment.arguments = arguments

        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "HOME")
            .commitNow()

        return fragment
    }

    @Test
    fun success_shows_list_and_hides_error() = runTest {
        // Movies call -> success
        coEvery { repo.getMovies() } returns Result.success(
            listOf(
                Movie(1, "Doctor Strange", "/p", "O"),
                Movie(2, "The Imitation Game", "/p2", "O2")
            )
        )

        val fragment = launchFragmentInContainer()

        // Let viewModelScope(Main) jobs run
        testScheduler.advanceUntilIdle()
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
        // Assert UI using findViewById (no binding access)
        val recycler = fragment.requireView().findViewById<RecyclerView>(R.id.recycler)
        val adapter = recycler.adapter as MoviesAdapter
        assertThat(adapter.itemCount, `is`(2))

        val error = fragment.requireView().findViewById<TextView>(R.id.errorView)
        val retry = fragment.requireView().findViewById<Button>(R.id.retryBtn)
        assertThat(error.visibility, `is`(View.GONE))
        assertThat(retry.visibility, `is`(View.GONE))

        coVerify(exactly = 1) { repo.getMovies() }
    }

    @Test
    fun failure_shows_error_and_retry_then_retry_succeeds() = runTest {
        // Movies call -> failure
        coEvery { repo.getMovies() } returns
            Result.failure(IllegalStateException("fail first"))

        val fragment = launchFragmentInContainer()

        testScheduler.advanceUntilIdle()
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

        val error = fragment.requireView().findViewById<TextView>(R.id.errorView)
        val retry = fragment.requireView().findViewById<Button>(R.id.retryBtn)

        // Failure state visible
        assertThat(error.visibility, `is`(View.VISIBLE))
        assertThat(retry.visibility, `is`(View.VISIBLE))
    }
}