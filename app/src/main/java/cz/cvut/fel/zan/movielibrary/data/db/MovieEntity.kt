package cz.cvut.fel.zan.movielibrary.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val localId : Int = 0,
    val imdbId : String = "",
    val movieTitle : String = "",
//    val movieImage : Int = 0,
    val movieImage : String = "",
    val rating : String = "",
    val totalSeasons : Int = 0,
//    val genre : List<Genre> = emptyList(),
    val genre : String = "",
    val country : String = "",
    val year : String = "",
    val description : String = "",
    val comments : List<String> = emptyList()
)
