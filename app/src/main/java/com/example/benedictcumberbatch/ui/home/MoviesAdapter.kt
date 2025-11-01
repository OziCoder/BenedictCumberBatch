package com.example.benedictcumberbatch.ui.home

import com.example.benedictcumberbatch.BuildConfig
import com.example.benedictcumberbatch.R
import com.example.benedictcumberbatch.domain.model.Movie
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load

class MoviesAdapter(private val onClick: (Movie) -> Unit)
    : ListAdapter<Movie, MoviesAdapter.VH>(diff) {

    object diff : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(a: Movie, b: Movie) = a.id == b.id
        override fun areContentsTheSame(a: Movie, b: Movie) = a == b
    }

    inner class VH(val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(m: Movie) {
            v.findViewById<TextView>(R.id.title).text = m.title
            val poster = v.findViewById<ImageView>(R.id.poster)
            val url = m.posterPath?.let { BuildConfig.TMDB_IMAGE_BASE + "w154" + it }
            poster.contentDescription = v.resources.getString(R.string.poster_for, m.title)
            poster.load(url) { placeholder(R.drawable.placeholder) }
            v.setOnClickListener { onClick(m) }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(LayoutInflater.from(p.context).inflate(R.layout.row_movie, p, false))

    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))
}