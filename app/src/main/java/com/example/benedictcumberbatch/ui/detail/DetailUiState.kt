package com.example.benedictcumberbatch.ui.detail

import com.example.benedictcumberbatch.domain.model.Movie
import com.example.benedictcumberbatch.domain.model.MovieDetail

data class DetailUiState(
    val loading: Boolean = true,
    val detail: MovieDetail? = null,
    val similar: List<Movie> = emptyList(),
    val error: String? = null
)