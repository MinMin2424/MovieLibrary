package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.ui.navigation.Routes
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.ui.utils.isLandscape
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieViewModel

@Composable
fun MainScreen(
    navController: NavController
) {
    Scaffold (
        topBar = { TopBarMainScreen(navController) },
        bottomBar = { BottomBarMainScreen(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.dark_ocean))
        ) {
            MainScreenContent(
                paddingValues = innerPadding,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMainScreen(navController: NavController) {
    TopAppBar(
        title = { Text(
            text = "Movie library",
            color = colorResource(R.color.white),
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        ) },
        navigationIcon = {
            IconButton(onClick = {/* TODO Open Nav Drawer */} ) {
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
                Icon(Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.profile),
                    tint = colorResource(R.color.white)
                )
            }
            IconButton(onClick = {
                /* Add movie */
                navController.navigate(Routes.AddMovie.route)
            }) {
                Icon(Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_movie),
                    tint = colorResource(R.color.white)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_blue)
        )
    )
}

@Composable
fun MainScreenContent(
    paddingValues: PaddingValues,
    navController: NavController
) {
    Column (
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        ListMovies(navController)
    }
}

@Composable
fun ListMovies(
    navController: NavController,
    viewModel: MovieViewModel = viewModel()
) {
    val movies by viewModel.movies.collectAsState()
    if (isLandscape()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.height(1000.dp)
        ) {
            items(movies) {movie ->
                MovieItem(
                    movieInfo = movie,
                    navController = navController
                )
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.height(1000.dp)
        ) {
            items(movies) {movie ->
                MovieItem(
                    movieInfo = movie,
                    navController = navController
                )
            }
        }
    }

}

@Composable
fun MovieItem(
    movieInfo: MovieInfo,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(110.dp)
            .clickable {
                println("navigating to movie with ID: ${movieInfo.localId}")
                navController.navigate("${Routes.Description.route}/${movieInfo.localId}")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = movieInfo.movieImage,
            contentDescription = movieInfo.movieTitle,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movieInfo.movieTitle,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.white)
        )
    }
}

@Composable
fun BottomBarMainScreen(
    navController: NavController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorResource(R.color.purple_blue)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.home),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "Home",
                    color = colorResource(R.color.white)
                ) },
            selected = currentRoute == Routes.Main.route,
            onClick = {
                navController.navigate(Routes.Main.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "Search",
                    color = colorResource(R.color.white)
                ) },
            selected = currentRoute == Routes.Search.route,
            onClick = {
                /* Open Search Screen */
                navController.navigate(Routes.Search.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = stringResource(R.string.genre),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "Genres",
                    color = colorResource(R.color.white)
                ) },
            selected = currentRoute == Routes.ListGenres.route,
            onClick = {
                /* Open List Genres Screen */
                navController.navigate(Routes.ListGenres.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(R.string.favorite_films),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "Favorite films",
                    color = colorResource(R.color.white)
                ) },
            selected = currentRoute == Routes.FavoriteMovies.route,
            onClick = {
                /* Open Favorite Films Screen */
                navController.navigate(Routes.FavoriteMovies.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        /*NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.profile),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "User profile",
                    color = colorResource(R.color.white)
                ) },
            selected = currentRoute == Routes.Profile.route,
            onClick = {
                *//* Open User Profile Screen *//*
                navController.navigate(Routes.Profile.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )*/
    }
}