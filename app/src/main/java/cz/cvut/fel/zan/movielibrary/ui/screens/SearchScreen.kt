package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.ui.navigation.Routes
import cz.cvut.fel.zan.movielibrary.ui.utils.isLandscape
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel()
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val movies by movieViewModel.movies.collectAsState()

    Scaffold(
        topBar = {
            SearchTopBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it },
                navController = navController
            )
        },
        bottomBar = { BottomBarMainScreen(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.dark_ocean))
        ) {
            val filteredMovies = remember(searchQuery, movies) {
                if (searchQuery.isBlank()) {
                    movies
                } else {
                    movies.filter { movie ->
                        movie.movieTitle.contains(searchQuery, ignoreCase = true)
                    }
                }
            }

            if (isLandscape()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies) { movie ->
                        SearchMovieItem(
                            movieInfo = movie,
                            onClick = {
                                navController.navigate("${Routes.Description.route}/${movie.localId}")
                            }
                        )
                    }
                }
            } else {
                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredMovies) { movie ->
                        SearchMovieItem(
                            movieInfo = movie,
                            onClick = {
                                navController.navigate("${Routes.Description.route}/${movie.localId}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    navController: NavController
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .padding(start = 0.dp)
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text("Search movies ...") },
                    modifier = Modifier
                        .width(350.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
                        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f)
                    ),
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search),
                            tint = Color.White
                        )
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_blue)
        ),
        actions = {
            IconButton(onClick = {
                /* Open profile */
                navController.navigate(Routes.Profile.route)
            }) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.profile),
                    tint = colorResource(R.color.white)
                )
            }
        }
    )
}

@Composable
fun SearchMovieItem(
    movieInfo: MovieInfo,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = movieInfo.movieImage,
                contentDescription = movieInfo.movieTitle,
                modifier = Modifier
                    .size(150.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = movieInfo.movieTitle,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Country: ${movieInfo.country}",
                    color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rating: ${movieInfo.rating}",
                    color = Color.White)
                Text(
                    text = "Year: ${movieInfo.year}",
                    color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}