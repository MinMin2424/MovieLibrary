package cz.cvut.fel.zan.movielibrary.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OmdbMovieResponse(
    @SerialName("Title") val title: String,
    @SerialName("Year") val year: String,
    @SerialName("Rated") val rated: String,
    @SerialName("Released") val released: String,
    @SerialName("Runtime") val runtime: String? = null,
    @SerialName("Genre") val genre: String,
    @SerialName("Director") val director: String? = null,
    @SerialName("Writer") val writer: String? = null,
    @SerialName("Actors") val actors: String? = null,
    @SerialName("Plot") val plot: String,
    @SerialName("Language") val language: String? = null,
    @SerialName("Country") val country: String,
    @SerialName("Awards") val awards: String? = null,
    @SerialName("Poster") val posterUrl: String,
    @SerialName("Ratings") val ratings: List<OmdbRating>? = null,
    @SerialName("Metascore") val metascore: String? = null,
    @SerialName("imdbRating") val imdbRating: String,
    @SerialName("imdbVotes") val imdbVotes: String? = null,
    @SerialName("imdbID") val imdbId: String,
    @SerialName("Type") val type: String? = null,
    @SerialName("DVD") val dvd: String? = null,
    @SerialName("BoxOffice") val boxOffice: String? = null,
    @SerialName("Production") val production: String? = null,
    @SerialName("Website") val website: String? = null,
    @SerialName("Response") val response: String?,
    @SerialName("totalSeasons") val totalSeasons: String? = null,
    @SerialName("Error") val error: String? = null
)

@Serializable
data class OmdbRating(
    @SerialName("Source") val source: String,
    @SerialName("Value") val value: String
)