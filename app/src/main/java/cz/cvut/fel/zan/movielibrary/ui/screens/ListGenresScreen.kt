package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import cz.cvut.fel.zan.movielibrary.data.local.Genre
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.ui.navigation.Routes
import cz.cvut.fel.zan.movielibrary.data.local.GetAllMovies
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieViewModel
import kotlin.collections.flatMap

@Composable
fun ListGenresScreen(
    navController: NavController,
    viewModel: MovieViewModel = viewModel()
) {
    Scaffold (
        topBar = { TopBarMainScreen(navController) },
        bottomBar = { BottomBarMainScreen(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                /*.background(colorResource(R.color.dark_ocean))*/
                .background(MaterialTheme.colorScheme.background)
        ) {
            ListGenreScreenContent(
                paddingValues = innerPadding,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ListGenreScreenContent(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: MovieViewModel
) {
    val movies = viewModel.movies.collectAsState()
    val movieList = movies.value
    val genres = movieList
        .flatMap { it.genre.split(",") }
        .map { it.trim() }
        .distinct()
        .sorted()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        for (genre in genres) {

            val moviesForGenre = movieList.filter { it.genre.contains(genre) }

            TextWithHorizontalLines(text = genre)

            if (moviesForGenre.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    for (movie in moviesForGenre) {
                        MovieItemGenre(
                            movie = movie,
                            navController = navController
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            } else {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No movies for this genre yet.",
                        /*color = Color.Gray,*/
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun TextWithHorizontalLines(text : String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            thickness = 1.dp,
            /*color = Color.Gray*/
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            /*color = Color.White,*/
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            thickness = 1.dp,
            /*color = Color.Gray*/
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun MovieItemGenre (
    movie : MovieInfo,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
            .clickable {
                navController.navigate("${Routes.Description.route}/${movie.localId}")
            }
    ) {
        AsyncImage(
            model = movie.movieImage,
            contentDescription = movie.movieTitle,
            modifier = Modifier.width(75.dp).height(90.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = movie.movieTitle,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            /*color = Color.White,*/
            color = MaterialTheme.colorScheme.primary
        )
    }
}