package cz.cvut.fel.zan.movielibrary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.cvut.fel.zan.movielibrary.ui.navigation.AppStarter
import cz.cvut.fel.zan.movielibrary.ui.theme.MovieLibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieLibraryTheme {
                AppStarter()
            }
        }
    }
}