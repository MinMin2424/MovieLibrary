package cz.cvut.fel.zan.movielibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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

@Composable
fun FavoriteScreen(
    favoriteMovies: List<MovieInfo>,
    navController: NavController
) {
    Scaffold (
        topBar = { TopBarFavoriteScreen(navController) },
        bottomBar = { BottomBarMainScreen(navController) },
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
                RenderListMovies(innerPadding, favoriteMovies)
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
fun RenderListMovies(paddingValues: PaddingValues, movies: List<MovieInfo>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        movies.forEach { movie ->
            FavoriteMovieItem(movie)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FavoriteMovieItem(movieInfo: MovieInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
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
                    onClick = { /* TODO Remove movie from favorites */ }
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