package cz.cvut.fel.zan.movielibrary

data class UserInfo(
    val name : String = "",
    val email : String = "",
    val profileImage : Int = 0,
    val registrationDate : String = "",
    val favoriteMoviesCount : Int = 0,
    val commentsCount : Int = 0,
    val listFavoriteMovies : List<MovieInfo> = emptyList()
)
