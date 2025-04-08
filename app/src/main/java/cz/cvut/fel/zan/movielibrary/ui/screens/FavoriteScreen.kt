package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.ui.navigation.Routes
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.ui.components.RenderSnackBar
import cz.cvut.fel.zan.movielibrary.ui.utils.isLandscape
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    favoriteMovies: List<MovieInfo>,
    navController: NavController,
    onRemoveFromFavorites: (MovieInfo) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold (
        topBar = { TopBarFavoriteScreen(navController) },
        bottomBar = { BottomBarMainScreen(navController) },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                RenderSnackBar(data)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.dark_ocean))
        ) {
            if (favoriteMovies.isEmpty()) {
                RenderEmptyListMovies()
            } else {
                RenderListMovies(
                    paddingValues = innerPadding,
                    navController = navController,
                    movies = favoriteMovies,
                    onRemoveFromFavorites = { movie ->
                        onRemoveFromFavorites(movie)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "${movie.movieTitle} removed from favorites",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun RenderEmptyListMovies() {
    Box(
        modifier = Modifier
            .padding(vertical = 150.dp)
            .padding(horizontal = 32.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(32.dp)
            )
            .height(320.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Search for your own favorite movies and add them here to save",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Icon(
                painter = painterResource(R.drawable.add_circle),
                contentDescription = stringResource(R.string.adding_movie),
                tint = Color.Gray,
                modifier = Modifier
                    .size(64.dp)
            )
        }
    }
}

@Composable
fun RenderListMovies(
    paddingValues: PaddingValues,
    navController: NavController,
    movies: List<MovieInfo>,
    onRemoveFromFavorites: (MovieInfo) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(if (isLandscape()) 2 else 1),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            FavoriteMovieItem(
                movieInfo = movie,
                navController = navController,
                onRemoveFromFavorites = onRemoveFromFavorites
            )
        }
    }
}

@Composable
fun FavoriteMovieItem(
    movieInfo: MovieInfo,
    navController: NavController,
    onRemoveFromFavorites: (MovieInfo) -> Unit
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
            .clickable {
                navController.navigate("${Routes.Description.route}/${movieInfo.movieId}")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(movieInfo.movieImage),
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
                    text = "Episodes: ${movieInfo.episodes}",
                    color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rating: ${movieInfo.rating}",
                    color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = { onRemoveFromFavorites(movieInfo) }
                ) {
                    Text(stringResource(R.string.remove))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarFavoriteScreen(
    navController: NavController
) {
    TopAppBar(
        title = { Text(
            text = "Favorite movies",
            color = colorResource(R.color.white),
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        ) },
        navigationIcon = {
            IconButton(onClick = { /* TODO Open Nav Drawer */ }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.menu),
                    tint = colorResource(R.color.white)
                )
            }
        },
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
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_blue)
        )
    )
}