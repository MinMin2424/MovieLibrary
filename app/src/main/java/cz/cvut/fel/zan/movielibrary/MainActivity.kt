package cz.cvut.fel.zan.movielibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.cvut.fel.zan.movielibrary.ui.theme.MovieLibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieLibraryTheme {
//                MainScreen()
                /*DescriptionScreen(
                    Doraemon()
                )*/
                /*FavoriteScreen(
//                    favoriteMovies = emptyList()
                    favoriteMovies = listOf(
                        Doraemon(),
                        Conan()
                    )
                )*/
                /*ProfileScreen(
                    UserInfo(
                        name = "MinMin Tranova",
                        email = "goldenmaknae2424@gmail.com",
                        profileImage = R.drawable.profile_picture,
                        registrationDate = "04.03.2025"
                    )
                )*/
                ListGenresScreen()
            }
        }
    }
}