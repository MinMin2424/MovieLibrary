package cz.cvut.fel.zan.movielibrary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int) : UserEntity

    @Query("SELECT * FROM user LIMIT 1")
    fun getFirstUser() : Flow<UserEntity>

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getUserCount() : Int
}