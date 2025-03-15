package cz.cvut.fel.zan.movielibrary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Routes(val route: String) {
    data object Main: Routes("Main")
    data object Description: Routes("Description")
    data object FavoriteMovies: Routes("FavoriteMovies")
    data object ListGenres: Routes("ListGenres")
    data object Profile: Routes("Profile")
}

@Composable
fun AppStarter() {
    val navController = rememberNavController()
    var userProfile by remember { mutableStateOf(
        UserInfo(
            name = "MinMin Tranova",
            email = "goldenmaknae2424@gmail.com",
            profileImage = R.drawable.user_profile,
            registrationDate = "04.03.2025",
            favoriteMoviesCount = 0,
            commentsCount = 0,
            listFavoriteMovies = emptyList()
        )
    ) }
    val onEditInfo = { newName: String, newEmail: String ->
        userProfile = userProfile.copy(name = newName, email = newEmail)
    }
    val addToFavorites = { movie: MovieInfo ->
        if (userProfile.listFavoriteMovies.none { it.movieId == movie.movieId }) {
            userProfile = userProfile.copy(
                listFavoriteMovies = userProfile.listFavoriteMovies + movie,
                favoriteMoviesCount = userProfile.favoriteMoviesCount + 1
            )
        }
    }

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
            val movie = GetAllMovies().find { it.movieId == movieId }
            if (movie != null) {
                DescriptionScreen(
                    movieInfo = movie,
                    navController = navController,
                    onAddToFavorites = { addToFavorites(movie) }
                )
            }
        }
        /* FAVORITE MOVIES SCREEN */
        composable(Routes.FavoriteMovies.route) {
            FavoriteScreen(
                favoriteMovies = userProfile.listFavoriteMovies,
                navController = navController
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
            ProfileScreen(
                userInfo = userProfile,
                navController = navController,
                onEditInfo = onEditInfo
            )
        }
    }
}