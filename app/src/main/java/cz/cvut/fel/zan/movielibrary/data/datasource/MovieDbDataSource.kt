package cz.cvut.fel.zan.movielibrary.data.datasource

import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.db.MovieDao
import cz.cvut.fel.zan.movielibrary.data.db.MovieEntity
import cz.cvut.fel.zan.movielibrary.data.local.GetAllMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDbDataSource(
    private val movieDao: MovieDao
) {
    suspend fun initializeDatabaseIfEmpty() {
        val count = movieDao.getMovieCount()
        if (count == 0) {
            GetAllMovies().forEach { movieInfo ->
                movieDao.insertMovie(movieInfo.toMovieEntity())
            }
        }
    }

    fun getAllMovies() : Flow<List<MovieInfo>> {
        return movieDao.getAllMovies().map { movieEntities ->
            movieEntities.map { e ->
                e.toMovieInfo()
            }
        }
    }

    suspend fun getMovieById(id: Int) : MovieInfo {
        println("Finding movie with ID: $id")
        val entity = movieDao.getMovieById(id)
            .also {
                println("MOVIE IS FOUND")
            }
        return entity.toMovieInfo()
    }

    suspend fun addComment(id: Int, comment: String) {
        val movie = movieDao.getMovieById(id)
        val updatedMovie = movie.copy(comments = movie.comments + comment)
        movieDao.updateMovie(updatedMovie)
    }

    suspend fun insertMovie(movieInfo: MovieInfo) {
        movieDao.insertMovie(movieInfo.toMovieEntity())
    }

    suspend fun deleteMovieById(id: Int) {
        movieDao.deleteMovieById(id)
    }
}

fun MovieInfo.toMovieEntity() : MovieEntity {
    return MovieEntity(
        id = movieId,
        movieTitle = movieTitle,
        movieImage = movieImage,
        rating = rating,
        episodes = episodes,
        genre = genre,
        country = country,
        description = description,
        comments = comments
    )
}

fun MovieEntity.toMovieInfo() : MovieInfo {
    return MovieInfo(
        movieId = id,
        movieTitle = movieTitle,
        movieImage = movieImage,
        rating = rating,
        episodes = episodes,
        genre = genre,
        country = country,
        description = description,
        comments = comments
    )
}