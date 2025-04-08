package cz.cvut.fel.zan.movielibrary.data.repository

import cz.cvut.fel.zan.movielibrary.data.datasource.MovieDbDataSource
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieDbDataSource: MovieDbDataSource
) {
    fun getAllMovies() : Flow<List<MovieInfo>> {
        return movieDbDataSource.getAllMovies()
    }

    suspend fun getMovieById(id: Int) =
        movieDbDataSource.getMovieById(id)

    suspend fun addComment(id: Int, comment: String) {
        movieDbDataSource.addComment(id, comment)
    }

    suspend fun insertMovie(movieInfo: MovieInfo) {
        movieDbDataSource.insertMovie(movieInfo)
    }

    suspend fun deleteMovieById(id: Int) {
        movieDbDataSource.deleteMovieById(id)
    }
}