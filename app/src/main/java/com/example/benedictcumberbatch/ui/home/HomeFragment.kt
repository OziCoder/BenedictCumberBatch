package com.example.benedictcumberbatch.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.benedictcumberbatch.R
import com.example.benedictcumberbatch.di.appContainer
import com.example.benedictcumberbatch.ui.common.HomeVMFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val vm: HomeViewModel by viewModels {
        HomeVMFactory(requireContext().appContainer.movieRepo)
    }
    private lateinit var adapter: MoviesAdapter
    private lateinit var progress: ProgressBar
    private lateinit var errorView: TextView
    private lateinit var retry: Button

    override fun onViewCreated(v: View, s: Bundle?) {
        progress = v.findViewById(R.id.progress)
        errorView = v.findViewById(R.id.errorView)
        retry = v.findViewById(R.id.retryBtn)
        val recycler = v.findViewById<RecyclerView>(R.id.recycler)

        adapter = MoviesAdapter { movie ->
            findNavController().navigate(
                R.id.action_home_to_detail,
                Bundle().apply { putParcelable("movie", movie) }
            )
        }
        recycler.adapter = adapter

        retry.setOnClickListener { vm.load() }

        collectUiStates()
    }

    private fun collectUiStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collect { st ->
                progress.isVisible = st.loading
                errorView.isVisible = st.error != null
                retry.isVisible = st.error != null
                errorView.text = st.error ?: ""
                adapter.submitList(st.items)
            }
        }
    }
}