package com.nrahmatd.moviecatalogue.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nrahmatd.moviecatalogue.R
import com.nrahmatd.moviecatalogue.data.MovieRepository
import com.nrahmatd.moviecatalogue.model.MovieModel
import com.nrahmatd.moviecatalogue.viewmodel.MoviesViewModel
import com.nrahmatd.moviecatalogue.viewmodel.ViewModelFactory

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(text = stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun HomeContent(
    moviesViewModel: MoviesViewModel = viewModel(factory = ViewModelFactory(MovieRepository())),
    navigateToDetail: (Int) -> Unit
) {
    val movies by moviesViewModel.movies.collectAsState()
    val query by moviesViewModel.query

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SearchBar(
                query,
                moviesViewModel::search
            )
        }
        items(movies, key = { it.id }) { movie ->
            CardItem(
                item = movie,
                modifier = Modifier
                    .clickable { navigateToDetail(movie.id) }
            )
        }
    }
}

@Composable
fun CardItem(
    item: MovieModel,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = item.imgMovie),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(width = 150.dp, height = 200.dp)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                Text(
                    modifier = modifier
                        .padding(8.dp),
                    text = item.titleMovie,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    modifier = modifier
                        .padding(8.dp),
                    text = item.dateMovie,
                    fontSize = 12.sp
                )
                Text(
                    modifier = modifier
                        .padding(8.dp),
                    text = item.descMovie,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.body2,
                    maxLines = 4
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearch(
    moviesViewModel: MoviesViewModel = viewModel(factory = ViewModelFactory(MovieRepository()))
) {
    val query by moviesViewModel.query
    SearchBar(
        query,
        moviesViewModel::search
    )
}

@Preview
@Composable
fun PreviewCardItem() {
    val movie = MovieModel(
        1012,
        R.drawable.poster_blackpanter,
        "Pacific Rim: Uprising",
        "March 21, 2018",
        "It has been ten years since The Battle of the Breach and the oceans are still, but restless. Vindicated by the victory at the Breach, the Jaeger program has evolved into the most powerful global defense force in human history. The PPDC now calls upon the best and brightest to rise up and become the next generation of heroes when the Kaiju threat returns.",
        "English",
        "1h 51m",
        "https://www.facebook.com/pacificrimmovie",
        "https://twitter.com/pacificrim",
        "https://www.instagram.com/pacificrimmovie",
        "Action"
    )
    CardItem(movie)
}

@Preview
@Composable
fun PreviewMovieList() {
//    HomeContent(navigateToDetail = )
}
