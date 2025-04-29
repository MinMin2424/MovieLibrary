package cz.cvut.fel.zan.movielibrary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY localId ASC")
    fun getAllMovies() : Flow<List<MovieEntity>>

    @Query("SELECT movieTitle FROM movie")
    suspend fun getAllTitles() : List<String>

    @Query("SELECT imdbId FROM movie")
    fun getAllImdbId() : List<String>

    @Query("SELECT COUNT(*) FROM movie")
    suspend fun getMovieCount() : Int

    @Query("SELECT * FROM movie WHERE localId = :localId")
    suspend fun getMovieById(localId: Int) : MovieEntity

    @Update
    suspend fun updateMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movie WHERE localId = :localId")
    suspend fun deleteMovieById(localId: Int)
}