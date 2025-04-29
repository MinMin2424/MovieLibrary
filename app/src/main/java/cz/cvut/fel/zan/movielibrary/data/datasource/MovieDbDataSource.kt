package cz.cvut.fel.zan.movielibrary.data.datasource

import android.util.Log
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.db.MovieDao
import cz.cvut.fel.zan.movielibrary.data.db.MovieEntity
import cz.cvut.fel.zan.movielibrary.data.local.GetAllMovies
import cz.cvut.fel.zan.movielibrary.data.repository.ApiCallResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDbDataSource(
    private val movieDao: MovieDao,
    private val movieRemoteDataSource: MovieRemoteDataSource? = null
) {
    suspend fun initializationDbIfEmpty() {
        val count = movieDao.getMovieCount()
        if (count == 0) {
            try {
                val apiSuccessTitle = loadFromApiByTitle()
                if (!apiSuccessTitle) {
                    Log.d("DBInit", "Using local movies as fallback")
                    loadLocalMovies()
                }
                val apiSuccessImdbId = loadFromApiByImdbId()
                if (!apiSuccessImdbId) {
                    Log.d("DBInit", "Failed to load movie by imdb from API")
                }
            } catch (e: Exception) {
                Log.e("DBInit", "Initialization failed", e)
                loadLocalMovies()
            }
        } else {
            updateDatabaseWithMissingMoviesByTitle()
            updateDatabaseWithMissingMoviesByImdbId()
        }
    }

    private suspend fun loadFromApiByTitle() : Boolean {
        val titles = getListMovieName()
        var apiSuccess = false
        titles.forEach { title ->
            when (val result = movieRemoteDataSource?.fetchMovieByTitle(title)) {
                is ApiCallResult.Success -> {
                    insertMovie(result.data)
                    apiSuccess = true
                    Log.d("DBInit", "Successfully loaded $title from API")
                }
                else -> Log.e("DBInit", "Failed to load $title from API")
            }
        }
        return apiSuccess
    }

    private suspend fun loadFromApiByImdbId() : Boolean {
        val imdbIds = getListImdbId()
        var apiSuccess = false
        imdbIds.forEach { imdbId ->
            when (val result = movieRemoteDataSource?.fetchMovieById(imdbId)) {
                is ApiCallResult.Success -> {
                    insertMovie(result.data)
                    apiSuccess = true
                    Log.d("DBInit", "Successfully loaded $imdbId from API")
                }
                else -> Log.e("DBInit", "Failed to load $imdbId from API")
            }
        }
        return apiSuccess
    }

    private suspend fun loadLocalMovies() {
        GetAllMovies().forEach {
            insertMovie(it)
        }
    }

    private fun getListMovieName() : List<String> {
        return listOf(
            "Alien: Romulus",
            "Jurassic World Rebirth",
            "Meg 2: The Trench",
            "Beast",
            "Meitantei Conan: Shikkoku no chaser",
            "Titanic",
            "The Penthouse: War in Life",
            "Doraemon",
            "Love You Seven Times",
            "Hidden Love",
            "Love Game in Eastern Fantasy",
            "Dragon Ball Z",
            "Ski Into Love",
            "My journey to you",
            "Train to Busan",
            "Moon Lovers: Scarlet Heart Ryeo",
            "Hotel Del Luna",
            "20th Century Girl"
        )
    }

    private fun getListImdbId() : List<String> {
        return listOf(
            "tt6263222" // String Girl Bong-soon
        )
    }

    private suspend fun updateDatabaseWithMissingMoviesByTitle() {
        val existingTitles = movieDao.getAllTitles().map {
            it.trim().lowercase()
        }
        val targetTitle = getListMovieName()

        targetTitle.forEach { title ->
            if (title.trim().lowercase() !in existingTitles) {
                when (val result = movieRemoteDataSource?.fetchMovieByTitle(title)) {
                    is ApiCallResult.Success -> {
                        insertMovie(result.data)
                        Log.d("DbUpdate", "Inserted missing movie $title")
                    }
                    else -> Log.e("DbUpdate", "Failed to fetch missing movie: $title")
                }
            }
        }
    }

    private suspend fun updateDatabaseWithMissingMoviesByImdbId() {
        val existingImdbIds = movieDao.getAllImdbId()
        val targetImdbIds = getListImdbId()

        targetImdbIds.forEach { imdbId ->
            if (imdbId !in existingImdbIds) {
                Log.d("StartUpdating...", "Start updating db with imdbId.")
                when (val result = movieRemoteDataSource?.fetchMovieById(imdbId)) {
                    is ApiCallResult.Success -> {
                        insertMovie(result.data)
                        Log.d("DbUpdate", "Inserted missing movie $imdbId")
                    }
                    else -> Log.e("DbUpdate", "Failed to fetch missing movie: $imdbId")
                }
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
        val entity = movieDao.getMovieById(id)
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
        localId = localId,
        imdbId = imdbId,
        movieTitle = movieTitle,
        movieImage = movieImage,
        rating = rating,
        totalSeasons = totalSeasons,
        genre = genre,
        country = country,
        year = year,
        description = description,
        comments = comments
    )
}

fun MovieEntity.toMovieInfo() : MovieInfo {
    return MovieInfo(
        localId = localId,
        imdbId = imdbId,
        movieTitle = movieTitle,
        movieImage = movieImage,
        rating = rating,
        totalSeasons = totalSeasons,
        genre = genre,
        country = country,
        year = year,
        description = description,
        comments = comments
    )
}

/*
suspend fun initializeDatabaseIfEmpty() {
    val count = movieDao.getMovieCount()
    if (count == 0) {
        GetAllMovies().forEach { movieInfo ->
            movieDao.insertMovie(movieInfo.toMovieEntity())
        }
    }
}*/
