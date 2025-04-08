package cz.cvut.fel.zan.movielibrary.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieLibraryDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieLibraryDatabase? = null

        fun getDatabase(
            context : Context,
        ) : MovieLibraryDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieLibraryDatabase::class.java,
                    name = "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}