package cz.cvut.fel.zan.movielibrary.data.repository

import cz.cvut.fel.zan.movielibrary.data.datasource.UserDbDataSource
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.local.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository(
    private val userDbDataSource: UserDbDataSource
) {
    fun getUser(): Flow<UserInfo> = userDbDataSource.getFirstUser()

    suspend fun insertUser(userInfo: UserInfo) {
        userDbDataSource.insertUser(userInfo)
    }

    suspend fun updateUser(userInfo: UserInfo) {
        userDbDataSource.updateUser(userInfo)
    }

    suspend fun getUserCount() : Int {
        return userDbDataSource.getUserCount()
    }

    suspend fun addToFavorites(movie: MovieInfo) {
        val currentUser = userDbDataSource.getFirstUser().first()
        val updatedFavorites = currentUser.listFavoriteMovies.toMutableList();
        if (updatedFavorites.none { it.localId == movie.localId }) {
            updatedFavorites.add(movie)
            val updatedUser = currentUser.copy(
                listFavoriteMovies = updatedFavorites,
                favoriteMoviesCount = updatedFavorites.size
            )
            userDbDataSource.updateUser(updatedUser)
        }
    }

    suspend fun removeFromFavorites(movie: MovieInfo) {
        val currentUser = userDbDataSource.getFirstUser().first()
        val updatedFavorites = currentUser.listFavoriteMovies.filter { it.localId != movie.localId }
        userDbDataSource.updateUser(
            currentUser.copy(
                listFavoriteMovies = updatedFavorites,
                favoriteMoviesCount = updatedFavorites.size
            )
        )
    }

    suspend fun addComment() {
        val currentUser = userDbDataSource.getFirstUser().first()
        val updateUser = currentUser.copy(
            commentsCount = currentUser.commentsCount + 1
        )
        userDbDataSource.updateUser(updateUser)
    }

    suspend fun getUserById(id: Int) : UserInfo {
        return userDbDataSource.getUserById(id)
    }
}