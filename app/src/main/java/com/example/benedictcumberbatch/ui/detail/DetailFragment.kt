package com.example.benedictcumberbatch.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.benedictcumberbatch.di.appContainer
import com.example.benedictcumberbatch.domain.model.Movie
import com.example.benedictcumberbatch.extensions.safeParcelable
import com.example.benedictcumberbatch.ui.common.DetailVMFactory

class DetailFragment : Fragment() {

    private val vm: DetailViewModel by viewModels {
        DetailVMFactory(requireContext().appContainer.movieRepo)
    }

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, s: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            val movie = requireArguments().safeParcelable<Movie>("movie")
            setContent { DetailScreen(movie) }
        }
}