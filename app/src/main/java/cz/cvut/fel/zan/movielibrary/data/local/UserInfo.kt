package cz.cvut.fel.zan.movielibrary.data.local

data class UserInfo(
    val id : Int = 1,
    val name : String = "",
    val email : String = "",
    val profileImage : Int = 0,
    val registrationDate : String = "",
    val favoriteMoviesCount : Int = 0,
    val commentsCount : Int = 0,
    val listFavoriteMovies : List<MovieInfo> = emptyList()
)
