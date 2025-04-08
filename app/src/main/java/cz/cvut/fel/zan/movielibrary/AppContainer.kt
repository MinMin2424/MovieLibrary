package cz.cvut.fel.zan.movielibrary

import android.content.Context
import cz.cvut.fel.zan.movielibrary.data.datasource.MovieDbDataSource
import cz.cvut.fel.zan.movielibrary.data.db.MovieLibraryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AppContainer {
    lateinit var movieLibraryDatabase: MovieLibraryDatabase
        private set

    val movieDbDataSource: MovieDbDataSource by lazy {
        val dataSource = MovieDbDataSource(movieLibraryDatabase.movieDao())
        CoroutineScope(Dispatchers.IO).launch {
            dataSource.initializeDatabaseIfEmpty()
        }
        dataSource
    }
    fun init(context: Context) {
        movieLibraryDatabase = MovieLibraryDatabase.getDatabase(context)
    }
}