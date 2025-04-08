package cz.cvut.fel.zan.movielibrary.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MovieInfo(
    val movieId : Int = 0,
    val movieTitle : String = "",
    val movieImage : Int = 0,
    val rating : String = "",
    val episodes : Int = 0,
    val genre : List<Genre> = emptyList(),
    val country : String = "",
    val description : String = "",
    val comments : List<String> = emptyList()
) : Parcelable