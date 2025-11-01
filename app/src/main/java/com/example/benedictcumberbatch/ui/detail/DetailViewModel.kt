package com.example.benedictcumberbatch.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benedictcumberbatch.data.repo.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: MovieRepository) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state

    fun setMovieId(id: Long) {
        if (_state.value.detail?.id == id && !_state.value.loading) return
        viewModelScope.launch {
            _state.value = DetailUiState(loading = true)
            val d = repo.movieDetail(id)
            val s = repo.similarMovies(id)
            _state.value = if (d.isSuccess) {
                DetailUiState(detail = d.getOrThrow(), similar = s.getOrNull().orEmpty(), loading = false)
            } else {
                DetailUiState(error = d.exceptionOrNull()?.message ?: "Error", loading = false)
            }
        }
    }
}