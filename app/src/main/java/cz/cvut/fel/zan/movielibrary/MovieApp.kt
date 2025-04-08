package cz.cvut.fel.zan.movielibrary

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.init(this)
        CoroutineScope(Dispatchers.IO).launch {
            AppContainer.movieDbDataSource
            AppContainer.userDbDataSource
        }
    }
}