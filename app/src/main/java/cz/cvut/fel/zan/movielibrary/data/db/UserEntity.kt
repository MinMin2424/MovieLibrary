package cz.cvut.fel.zan.movielibrary.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name : String = "",
    val email : String = "",
    val profileImage : Int = 0,
    val registrationDate : String = "",
    val favoriteMoviesCount : Int = 0,
    val commentsCount : Int = 0,
    val listFavoriteMovies : List<MovieInfo> = emptyList()
)
