package cz.cvut.fel.zan.movielibrary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.cvut.fel.zan.movielibrary.data.dataStore.clearPreferences
import cz.cvut.fel.zan.movielibrary.ui.navigation.AppStarter
import cz.cvut.fel.zan.movielibrary.ui.theme.MovieLibraryTheme
import cz.cvut.fel.zan.movielibrary.ui.viewModel.ThemeViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /*lifecycleScope.launch {
            clearPreferences(this@MainActivity)
            Log.d("MainActivity", "DataStore cleared for testing")
        }*/
        Log.d("IMAGE_DEBUG", "Drawable ID: ${R.drawable.user_profile}")
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(
                factory = ThemeViewModel.provideFactory(this)
            )
            MovieLibraryTheme {
                AppStarter(themeViewModel)
            }
        }
    }
}