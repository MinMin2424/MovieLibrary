package cz.cvut.fel.zan.movielibrary

import android.content.Context
import android.util.Log
import cz.cvut.fel.zan.movielibrary.data.datasource.MovieDbDataSource
import cz.cvut.fel.zan.movielibrary.data.datasource.MovieRemoteDataSource
import cz.cvut.fel.zan.movielibrary.data.datasource.UserDbDataSource
import cz.cvut.fel.zan.movielibrary.data.db.MovieLibraryDatabase
import cz.cvut.fel.zan.movielibrary.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AppContainer {
    lateinit var movieLibraryDatabase: MovieLibraryDatabase
        private set

    private val movieRemoteDataSource: MovieRemoteDataSource by lazy {
        MovieRemoteDataSource(RetrofitClient.omdbApi).also {
            CoroutineScope(Dispatchers.IO).launch {
                val apiWorking = it.testApiConnection()
                Log.d("AppInit", "API connection test: $apiWorking")
            }
        }
    }

    val movieDbDataSource: MovieDbDataSource by lazy {
        val dataSource = MovieDbDataSource(movieLibraryDatabase.movieDao(), movieRemoteDataSource)
        CoroutineScope(Dispatchers.IO).launch {
//            dataSource.initializeDatabaseIfEmpty()
            dataSource.initializationDbIfEmpty()
        }
        dataSource
    }

    val userDbDataSource: UserDbDataSource by lazy {
        val dataSource = UserDbDataSource(movieLibraryDatabase.userDao())
        CoroutineScope(Dispatchers.IO).launch {
            dataSource.initializeDatabaseIfEmpty()
        }
        dataSource
    }

    fun init(context: Context) {
        movieLibraryDatabase = MovieLibraryDatabase.getDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
//            dataSource.initializeDatabaseIfEmpty()
            movieDbDataSource.initializationDbIfEmpty()
        }
    }
}