package com.example.benedictcumberbatch.ui.home

import com.example.benedictcumberbatch.domain.model.Movie

data class HomeUiState(
    val loading: Boolean = false,
    val items: List<Movie> = emptyList(),
    val error: String? = null
)