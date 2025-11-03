package com.example.benedictcumberbatch.ui.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.benedictcumberbatch.R
import com.example.benedictcumberbatch.domain.model.Movie
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33])
class DetailComposeTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    // --- Helper: scroll to a node if needed, then assert it's displayed ---
    private fun assertTextDisplayedWithScroll(text: String) {
        val matcher = hasText(text, substring = true, ignoreCase = true)

        // If there is a scrollable parent, try to scroll to the node first
        val hasScrollable = composeRule.onAllNodes(hasScrollAction()).fetchSemanticsNodes().isNotEmpty()
        if (hasScrollable) {
            composeRule.onNode(hasScrollAction()).performScrollToNode(matcher)
        }
        composeRule.onNode(matcher).assertIsDisplayed()
    }

    @Test
    fun shows_title_and_overview_text_when_present() {
        val movie = Movie(
            id = 1,
            title = "Doctor Strange",
            posterPath = "/p",
            overView = "Master of the mystic arts"
        )

        composeRule.setContent { DetailScreen(movie = movie) }

        assertTextDisplayedWithScroll("Doctor Strange")
        assertTextDisplayedWithScroll(composeRule.activity.getString(R.string.movie_overview))
        assertTextDisplayedWithScroll("Master of the mystic arts")
    }

    @Test
    fun shows_no_overview_placeholder_when_overview_is_null() {
        val movie = Movie(
            id = 2,
            title = "The Imitation Game",
            posterPath = "/p2",
            overView = ""
        )

        composeRule.setContent { DetailScreen(movie = movie) }

        assertTextDisplayedWithScroll("The Imitation Game")
        assertTextDisplayedWithScroll(composeRule.activity.getString(R.string.movie_overview))
        assertTextDisplayedWithScroll(composeRule.activity.getString(R.string.no_overview))
    }
}