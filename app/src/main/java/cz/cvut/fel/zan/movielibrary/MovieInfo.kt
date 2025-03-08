package cz.cvut.fel.zan.movielibrary

data class MovieInfo(
    val movieTitle : String = "",
    val movieImage : Int = 0,
    val rating : String = "",
    val episodes : Int = 0,
    val country : String = "",
    val description : String = "",
    val comments : List<String> = emptyList()
)
