package cz.cvut.fel.zan.movielibrary.data.remote

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://www.omdbapi.com/"
    const val API_KEY = "5548ada7"
    private val json = Json { ignoreUnknownKeys = true }

    val omdbApi: OmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(OmdbApiService::class.java)
    }
}
