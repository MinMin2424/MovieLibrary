package cz.cvut.fel.zan.movielibrary.data.datasource

import android.util.Log
import coil3.network.HttpException
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.remote.OmdbApiService
import cz.cvut.fel.zan.movielibrary.data.remote.RetrofitClient
import cz.cvut.fel.zan.movielibrary.data.repository.ApiCallResult
import java.io.IOException

class MovieRemoteDataSource(
    private val omdbApi: OmdbApiService
) {
    suspend fun testApiConnection(): Boolean {
        return try {
            val response = omdbApi.getMovieByTitle(
                title = "Titanic",
                apikey = RetrofitClient.API_KEY,
                plot = "short"
            )
            Log.d("APITest", "API Response: ${response.title}")
            true
        } catch (e: Exception) {
            Log.e("APITest", "API Error", e)
            false
        }
    }

    suspend fun fetchMovieByTitle(title: String) : ApiCallResult<MovieInfo> {
        return try {
            val response = omdbApi.getMovieByTitle(
                title = title,
                apikey = RetrofitClient.API_KEY,
                plot = "full"
            )
            Log.d("API", "Fetching $title with apikey=${RetrofitClient.API_KEY}")
            if (response.imdbId.isBlank()) {
                Log.e("APIError", "Missing IMDb ID for $title")
            }
            if (response.response != "True") {
                Log.e("APIError", "Invalid API response for $title: ${response.title}")
            }
            if (response.response == "True") {
                ApiCallResult.success(MovieInfo.fromOmdbResponse(response))
            } else {
                ApiCallResult.error(message = "Movie not found or API error")
            }
        } catch (e: Exception) {
            ApiCallResult.error(
                exception = e,
                message = when (e) {
                    is IOException -> "Error with connecting"
                    is HttpException -> "Movie cannot be found"
                    else -> "Unknown error"
                }
            )
        }
    }

    suspend fun fetchMovieById(imdbId: String) : ApiCallResult<MovieInfo> {
        return try {
            Log.d("FetchMovieById", "Start fetching movie by imdbId")
            val response = omdbApi.getMovieById(
                imdbId = imdbId,
                apikey = RetrofitClient.API_KEY,
                plot = "full"
            )
            Log.d("API", "Fetching $imdbId with apikey=${RetrofitClient.API_KEY}")
            if (response.error != null) {
                Log.e("APIError", "Cannot fetching movie by imdbId: ${response.error}")
            }
            if (response.title.isBlank()) {
                Log.e("APIError", "Missing title for $imdbId")
            }
            if (response.response != "True") {
                Log.e("APIError", "Invalid API response for $imdbId: ${response.imdbId}")
            }
            if (response.response == "True") {
                ApiCallResult.success(MovieInfo.fromOmdbResponse(response))
            } else {
                ApiCallResult.error(message = "Movie not found or API error")
            }
        } catch (e: Exception) {
            Log.e("FetchMovieById", "Exception during fetch: ${e.message}", e)
            ApiCallResult.error(
                exception = e,
                message = when (e) {
                    is IOException -> "Error with connecting"
                    is HttpException -> "Movie cannot be found"
                    else -> "Unknown error"
                }
            )
        }
    }
}