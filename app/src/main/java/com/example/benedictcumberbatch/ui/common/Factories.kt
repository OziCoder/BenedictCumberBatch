package com.example.benedictcumberbatch.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.benedictcumberbatch.data.repo.MovieRepository
import com.example.benedictcumberbatch.ui.detail.DetailViewModel
import com.example.benedictcumberbatch.ui.home.HomeViewModel

class HomeVMFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repo) as T
        }
        error("Unknown VM: $modelClass")
    }
}

class DetailVMFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repo) as T
        }
        error("Unknown VM: $modelClass")
    }
}