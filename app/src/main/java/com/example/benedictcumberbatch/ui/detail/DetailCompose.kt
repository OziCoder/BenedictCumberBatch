package com.example.benedictcumberbatch.ui.detail
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.benedictcumberbatch.BuildConfig
import com.example.benedictcumberbatch.R
import com.example.benedictcumberbatch.domain.model.Movie

@Composable
fun DetailScreen(movie: Movie?) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        item {
            movie?.title?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.height(12.dp))
            AsyncImage(
                model = movie?.posterPath?.let { BuildConfig.TMDB_IMAGE_BASE + "w342" + it },
                contentDescription = movie?.title?.let { stringResource(R.string.poster_for, it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.movie_overview),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(12.dp))
            Text(movie?.overView?.ifBlank { stringResource(R.string.no_overview) } ?: "")
        }
        /*if (movie.isNotEmpty()) {
            item { Spacer(Modifier.height(24.dp)); Text(stringResource(R.string.similar_movies), style = MaterialTheme.typography.titleMedium) }
            items(similar, key = { it.id!! }) { m ->
                Row(
                    Modifier.fillMaxWidth().clickable { *//* optional: navigate to another detail *//* }.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = m.posterPath?.let { BuildConfig.TMDB_IMAGE_BASE + "w154" + it },
                        contentDescription = m.title?.let { stringResource(R.string.poster_for, it) },
                        modifier = Modifier.size(width = 56.dp, height = 84.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    m.title?.let { Text(it, style = MaterialTheme.typography.bodyLarge) }
                }
            }
        }*/
    }
}