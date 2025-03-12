package cz.cvut.fel.zan.movielibrary

data class MovieInfo(
    val movieTitle : String = "",
    val movieImage : Int = 0,
    val rating : String = "",
    val episodes : Int = 0,
    val genre : List<Genre> = emptyList(),
    val country : String = "",
    val description : String = "",
    val comments : List<String> = emptyList()
)