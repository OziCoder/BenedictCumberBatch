package com.example.benedictcumberbatch.data.remote

import com.example.benedictcumberbatch.domain.model.Movie
import com.example.benedictcumberbatch.domain.model.MovieDetail

fun Results.toDomain() = Movie(id = id, title = title, posterPath = posterPath, overView = overview)
fun MovieDetailDto.toDomain() = MovieDetail(id, title, poster_path, overview)