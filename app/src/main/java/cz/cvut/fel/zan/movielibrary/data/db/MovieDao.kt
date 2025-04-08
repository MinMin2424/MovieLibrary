package cz.cvut.fel.zan.movielibrary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY id ASC")
    fun getAllMovies() : Flow<List<MovieEntity>>

    @Query("SELECT COUNT(*) FROM movie")
    suspend fun getMovieCount() : Int

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieById(id: Int) : MovieEntity

    @Update
    suspend fun updateMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteMovieById(id: Int)
}