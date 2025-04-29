package cz.cvut.fel.zan.movielibrary.data.local

import android.os.Parcelable
import cz.cvut.fel.zan.movielibrary.data.remote.OmdbMovieResponse
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.math.abs
import kotlin.random.Random

@Parcelize
@Serializable
data class MovieInfo(
    val localId : Int = 0,
    val imdbId : String = "",
    val movieTitle : String = "",
    val movieImage : String = "",
    val rating : String = "",
    val totalSeasons : Int = 0,
    val genre : String = "",
    val country : String = "",
    val year : String = "",
    val description : String = "",
    val comments : List<String> = emptyList()
) : Parcelable {

    companion object {
        fun fromOmdbResponse(response: OmdbMovieResponse) : MovieInfo {
            if (response.response != "True") {
                throw IllegalArgumentException("Invalid API response.")
            }
            return MovieInfo(
                localId = generateLocalId(response.imdbId),
                imdbId = response.imdbId,
                movieTitle = response.title,
                movieImage = response.posterUrl,
                rating = response.imdbRating,
                totalSeasons = response.totalSeasons?.toIntOrNull() ?: 0,
                genre = response.genre,
                country = response.country,
                year = response.year,
                description = response.plot
            )
        }

        private fun generateLocalId(imdbId: String?) : Int {
            return imdbId?.replace("tt", "")
                ?.toIntOrNull()
                ?: abs(imdbId?.hashCode() ?: Random.nextInt())
        }
    }
}