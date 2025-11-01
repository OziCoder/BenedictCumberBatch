package com.example.benedictcumberbatch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(var id: Int?, var title: String?, var posterPath: String?, var overView: String?) : Parcelable
data class MovieDetail(val id: Long, val title: String, val posterPath: String?, val overview: String)