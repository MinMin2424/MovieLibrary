package cz.cvut.fel.zan.movielibrary

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.init(this)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("AppInit", "Initializing DB...")
                AppContainer.movieDbDataSource
                Log.d("AppInit", "DB initialized successfully")
            } catch (e: Exception) {
                Log.e("AppInit", "DB initialization failed", e)
            }
            AppContainer.userDbDataSource
        }
    }
}