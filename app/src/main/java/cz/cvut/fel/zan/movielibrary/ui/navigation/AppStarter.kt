package cz.cvut.fel.zan.movielibrary.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.ui.screens.DescriptionScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.FavoriteScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.ListGenresScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.MainScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.ProfileScreen
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieEditEvent
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieViewModel
import cz.cvut.fel.zan.movielibrary.ui.viewModel.ProfileScreenEditEvent
import cz.cvut.fel.zan.movielibrary.ui.viewModel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AppStarter() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val movieViewModel: MovieViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Main.route
    ) {
        /* MAIN SCREEN */
        composable(Routes.Main.route) {
            MainScreen(
                navController = navController
            )
        }
        /* MOVIE'S DESCRIPTION SCREEN */
        composable(
            "${Routes.Description.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType} )
        ) {navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt("movieId") ?: 0
            val movie by produceState<MovieInfo?>(initialValue = null) {
                value = withContext(Dispatchers.IO) {
                    movieViewModel.loadMovieById(movieId)
                }
            }
            movie?.let { nonNullMovie ->
                DescriptionScreen(
                    movie = nonNullMovie,
                    navController = navController,
                    /* TODO Changed onEditInfo -> onEvent method */
//                onAddToFavorites = { userViewModel.addToFavorites(movie) },
                    onAddToFavorites = { userViewModel.onEvent(ProfileScreenEditEvent.AddToFavoritesChanged(nonNullMovie)) },
                    movieViewModel = movieViewModel,
                    userViewModel = userViewModel
                )
            }


        }
        /* FAVORITE MOVIES SCREEN */
        composable(Routes.FavoriteMovies.route) {
            val favoriteMovies by userViewModel.userInfo.collectAsState()
            FavoriteScreen(
                favoriteMovies = favoriteMovies.listFavoriteMovies,
                navController = navController,
                /* TODO Changed onEditInfo -> onEvent method */
//                onRemoveFromFavorites = { movie -> userViewModel.removeFromFavorites(movie) }
                onRemoveFromFavorites = { movie -> userViewModel.onEvent(ProfileScreenEditEvent.RemoveFromFavoritesChanged(movie)) }
            )
        }
        /* LIST GENRES SCREEN */
        composable(Routes.ListGenres.route) {
            ListGenresScreen(
                navController = navController
            )
        }
        /* USER PROFILE SCREEN */
        composable(Routes.Profile.route) {
            val userInfo by userViewModel.userInfo.collectAsState()
            ProfileScreen(
                userInfo = userInfo,
                navController = navController,
                /* TODO Changed onEditInfo -> onEvent method */
//                onEditInfo = { newName, newEmail -> userViewModel.editUserInfo(newName, newEmail) }
                onEditInfo = { newName, newEmail -> userViewModel.onEvent(ProfileScreenEditEvent.UserInfoChanged(newName, newEmail))}
            )
        }
    }
}