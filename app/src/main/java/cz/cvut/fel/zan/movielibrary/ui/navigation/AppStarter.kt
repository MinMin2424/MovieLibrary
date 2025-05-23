package cz.cvut.fel.zan.movielibrary.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.cvut.fel.zan.movielibrary.data.dataStore.getLastOpenedScreen
import cz.cvut.fel.zan.movielibrary.data.dataStore.saveLastOpenedScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.AddMovieScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.DescriptionScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.FavoriteScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.ListGenresScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.MainScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.ProfileScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.SearchScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.SplashScreen
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieViewModel
import cz.cvut.fel.zan.movielibrary.ui.viewModel.ProfileScreenEditEvent
import cz.cvut.fel.zan.movielibrary.ui.viewModel.ThemeViewModel
import cz.cvut.fel.zan.movielibrary.ui.viewModel.UserViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppStarter(
    themeViewModel: ThemeViewModel
) {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val movieViewModel: MovieViewModel = viewModel()

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    val lastOpenedScreen by getLastOpenedScreen(context).collectAsState(initial = null)

    LaunchedEffect(Unit) {
        delay(1000L)
        val targetRoute = when {
            lastOpenedScreen != null && lastOpenedScreen != Routes.Main.route -> lastOpenedScreen!!
            else -> Routes.Main.route
        }
        navController.navigate(targetRoute) {
            popUpTo(Routes.SplashScreen.route) {
                inclusive = true
            }
        }
        isLoading = false
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(currentBackStackEntry) {
        if (!isLoading) {
            currentBackStackEntry?.destination?.route?.let { route ->
                if (route != Routes.SplashScreen.route) {
                    saveLastOpenedScreen(context, route)
                }
            }
        }
    }

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Routes.SplashScreen.route
        ) {
            /* SPLASH SCREEN */
            composable(Routes.SplashScreen.route) {
                SplashScreen(
                    onNavigate = {}
                )
            }
            /* MAIN SCREEN */
            composable(Routes.Main.route) {
                MainScreen(
                    navController = navController,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }
            /* MOVIE'S DESCRIPTION SCREEN */
            composable(
                "${Routes.Description.route}/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType} )
            ) {navBackStackEntry ->
                LaunchedEffect(Unit) {
                    saveLastOpenedScreen(
                        context = context,
                        route = "${Routes.Description.route}/${navBackStackEntry.arguments?.getInt("movieId")}"
                    )
                }
                val movieId = navBackStackEntry.arguments?.getInt("movieId") ?: 0
                val movie by movieViewModel.currentMovie.collectAsState()
                LaunchedEffect(movieId) {
                    movieViewModel.loadMovieById(movieId)
                }
                movie?.let { nonNullMovie ->
                    DescriptionScreen(
                        movie = nonNullMovie,
                        navController = navController,
                        onAddToFavorites = { userViewModel.onEvent(ProfileScreenEditEvent.AddToFavoritesChanged(nonNullMovie)) },
                        movieViewModel = movieViewModel,
                        userViewModel = userViewModel,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }
            }
            /* FAVORITE MOVIES SCREEN */
            composable(Routes.FavoriteMovies.route) {
                LaunchedEffect(Unit) {
                    saveLastOpenedScreen(
                        context = context,
                        route = Routes.FavoriteMovies.route
                    )
                }
                val favoriteMovies by userViewModel.userInfo.collectAsState()
                FavoriteScreen(
                    favoriteMovies = favoriteMovies!!.listFavoriteMovies,
                    navController = navController,
                    onRemoveFromFavorites = { movie -> userViewModel.onEvent(ProfileScreenEditEvent.RemoveFromFavoritesChanged(movie)) }
                )
            }
            /* LIST GENRES SCREEN */
            composable(Routes.ListGenres.route) {
                LaunchedEffect(Unit) {
                    saveLastOpenedScreen(
                        context = context,
                        route = Routes.ListGenres.route
                    )
                }
                ListGenresScreen(
                    navController = navController
                )
            }
            /* USER PROFILE SCREEN */
            composable(Routes.Profile.route) {
                LaunchedEffect(Unit) {
                    saveLastOpenedScreen(
                        context = context,
                        route = Routes.Profile.route
                    )
                }
                val userInfo by userViewModel.userInfo.collectAsState()
                ProfileScreen(
                    userInfo = userInfo!!,
                    navController = navController,
                    onEditInfo = { newName, newEmail -> userViewModel.onEvent(
                        ProfileScreenEditEvent.UserInfoChanged(newName, newEmail))
                    },
                    themeViewModel = themeViewModel
                )
            }
            /* SEARCH SCREEN */
            composable(Routes.Search.route) {
                LaunchedEffect(Unit) {
                    saveLastOpenedScreen(
                        context = context,
                        route = Routes.Search.route
                    )
                }
                SearchScreen(
                    navController = navController
                )
            }
            /* ADD MOVIE SCREEN */
            composable(Routes.AddMovie.route) {
                LaunchedEffect(Unit) {
                    saveLastOpenedScreen(
                        context = context,
                        route = Routes.AddMovie.route
                    )
                }
                AddMovieScreen(
                    navController = navController
                )
            }
        }
    }
}