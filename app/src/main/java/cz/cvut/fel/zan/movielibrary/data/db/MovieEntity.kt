package cz.cvut.fel.zan.movielibrary.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.cvut.fel.zan.movielibrary.data.local.Genre

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieTitle : String = "",
    val movieImage : Int = 0,
    val rating : String = "",
    val episodes : Int = 0,
    val genre : List<Genre> = emptyList(),
    val country : String = "",
    val description : String = "",
    val comments : List<String> = emptyList()
)
