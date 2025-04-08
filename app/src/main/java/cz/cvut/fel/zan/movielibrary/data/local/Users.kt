package cz.cvut.fel.zan.movielibrary.data.local

import cz.cvut.fel.zan.movielibrary.R

fun GetAllUsers() : List<UserInfo> {
    return listOf(
        getMinMin()
    )
}

fun getMinMin() : UserInfo {
    return UserInfo(
        id = 1,
        name = "MinMin Tranova",
        email = "goldenmaknae2424@gmail.com",
        profileImage = R.drawable.user_profile,
        registrationDate = "04.03.2025",
        favoriteMoviesCount = 0,
        commentsCount = 0,
        listFavoriteMovies = emptyList()
    )
}