package cz.cvut.fel.zan.movielibrary.ui.navigation

sealed class Routes(val route: String) {
    data object Main: Routes("Main")
    data object Description: Routes("Description")
    data object FavoriteMovies: Routes("FavoriteMovies")
    data object ListGenres: Routes("ListGenres")
    data object Profile: Routes("Profile")
}