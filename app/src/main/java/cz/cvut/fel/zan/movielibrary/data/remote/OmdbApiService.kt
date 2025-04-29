package cz.cvut.fel.zan.movielibrary.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {

    @GET("/")
    suspend fun getMovieByTitle(
        @Query("t") title: String,
        @Query("apikey") apikey: String,
        @Query("plot") plot: String = "full"
    ) : OmdbMovieResponse

    @GET("/")
    suspend fun getMovieById(
        @Query("i") imdbId: String,
        @Query("apikey") apikey: String,
        @Query("plot") plot: String = "full"
    ) : OmdbMovieResponse
}