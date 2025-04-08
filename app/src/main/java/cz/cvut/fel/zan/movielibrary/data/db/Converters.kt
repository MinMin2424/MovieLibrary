package cz.cvut.fel.zan.movielibrary.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cz.cvut.fel.zan.movielibrary.data.local.Genre

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromGenreList(value: List<Genre>?) : String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toGenreList(value: String) : List<Genre> {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?) : String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String) : List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}