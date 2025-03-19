package cz.cvut.fel.zan.movielibrary.ui.navigation

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
import cz.cvut.fel.zan.movielibrary.ui.screens.DescriptionScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.FavoriteScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.ListGenresScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.MainScreen
import cz.cvut.fel.zan.movielibrary.ui.screens.ProfileScreen
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.data.GetAllMovies
import cz.cvut.fel.zan.movielibrary.data.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.UserInfo

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
    var movies by remember { mutableStateOf(GetAllMovies()) }
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
    val removeFromFavorites = { movie: MovieInfo ->
        userProfile = userProfile.copy(
            listFavoriteMovies = userProfile.listFavoriteMovies.filter { it.movieId != movie.movieId },
            favoriteMoviesCount = userProfile.favoriteMoviesCount - 1
        )
    }
    val addComment = { movieId: Int, comment: String ->
        movies = movies.map { movie ->
            if (movie.movieId == movieId) {
                movie.addComment(comment)
            } else {
                movie
            }
        }
        userProfile = userProfile.copy(
            commentsCount = userProfile.commentsCount + 1
        )
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
            val movie = movies.find { it.movieId == movieId }
            if (movie != null) {
                DescriptionScreen(
                    movieInfo = movie,
                    navController = navController,
                    onAddToFavorites = { addToFavorites(movie) },
                    onAddComment = {
                            comment -> addComment(movieId, comment)
                    }
                )
            }
        }
        /* FAVORITE MOVIES SCREEN */
        composable(Routes.FavoriteMovies.route) {
            FavoriteScreen(
                favoriteMovies = userProfile.listFavoriteMovies,
                navController = navController,
                onRemoveFromFavorites = removeFromFavorites
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