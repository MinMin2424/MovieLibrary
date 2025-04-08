package cz.cvut.fel.zan.movielibrary.data.datasource

import cz.cvut.fel.zan.movielibrary.data.db.UserDao
import cz.cvut.fel.zan.movielibrary.data.db.UserEntity
import cz.cvut.fel.zan.movielibrary.data.local.getAllUsers
import cz.cvut.fel.zan.movielibrary.data.local.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDbDataSource(
    private val userDao: UserDao
) {

    suspend fun initializeDatabaseIfEmpty() {
        val count = userDao.getUserCount()
        if (count == 0) {
            getAllUsers().forEach { userInfo ->
                userDao.insertUser(userInfo.toUserEntity())
            }
        }
    }

    suspend fun getUserCount() : Int {
        return userDao.getUserCount()
    }

    suspend fun insertUser(userInfo: UserInfo) {
        userDao.insertUser(userInfo.toUserEntity())
    }

    suspend fun updateUser(userInfo: UserInfo) {
        userDao.updateUser(userInfo.toUserEntity())
    }

    fun getFirstUser() : Flow<UserInfo> {
        return userDao.getFirstUser().map { it.toUserInfo() }
    }

    suspend fun getUserById(id: Int) : UserInfo {
        return userDao.getUserById(id).toUserInfo()
    }
}

fun UserInfo.toUserEntity() : UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        profileImage = profileImage,
        registrationDate = registrationDate,
        favoriteMoviesCount = favoriteMoviesCount,
        commentsCount = commentsCount,
        listFavoriteMovies = listFavoriteMovies
    )
}

fun UserEntity.toUserInfo() : UserInfo {
    return UserInfo(
        id = id,
        name = name,
        email = email,
        profileImage = profileImage,
        registrationDate = registrationDate,
        favoriteMoviesCount = favoriteMoviesCount,
        commentsCount = commentsCount,
        listFavoriteMovies = listFavoriteMovies
    )
}