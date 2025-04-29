package cz.cvut.fel.zan.movielibrary.data.repository

import cz.cvut.fel.zan.movielibrary.data.datasource.MovieDbDataSource
import cz.cvut.fel.zan.movielibrary.data.datasource.MovieRemoteDataSource
import cz.cvut.fel.zan.movielibrary.data.local.Genre
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val movieDbDataSource: MovieDbDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) {
    fun getAllMovies() : Flow<List<MovieInfo>> {
        return movieDbDataSource.getAllMovies()
    }

    suspend fun getAllTitles() : List<String> {
        return movieDbDataSource.getAllTitles()
    }

    fun getAllImdbIds() : List<String> {
        return movieDbDataSource.getAllImdbIs()
    }

    suspend fun getMovieById(localId: Int) =
        movieDbDataSource.getMovieById(localId)

    suspend fun addComment(localId: Int, comment: String) {
        movieDbDataSource.addComment(localId, comment)
    }

    suspend fun insertMovie(movieInfo: MovieInfo) {
        movieDbDataSource.insertMovie(movieInfo)
    }

    suspend fun deleteMovieById(localId: Int) {
        movieDbDataSource.deleteMovieById(localId)
    }

    suspend fun fetchAndStoreMovieFromWeb(title: String) : MovieResult {
        val existingTitles = movieDbDataSource.getAllTitles()
        if (title in existingTitles) {
            return MovieResult.MOVIE_ALREADY_EXISTS
        }
        return when (val result = movieRemoteDataSource.fetchMovieByTitle(title)) {
            is ApiCallResult.Success -> {
                movieDbDataSource.insertMovie(result.data)
                MovieResult.SUCCESS
            }
            else -> MovieResult.MOVIE_NOT_FOUND

        }
    }

    suspend fun fetchAndStoreMovieById(imdbId: String) : ApiCallResult<MovieInfo> {
        return when (val result = movieRemoteDataSource.fetchMovieById(imdbId)) {
            is ApiCallResult.Success -> {
                movieDbDataSource.insertMovie(result.data)
                ApiCallResult.success(result.data)
            }
            is ApiCallResult.Error -> result
            ApiCallResult.Loading -> ApiCallResult.loading()
        }
    }
}