package cz.cvut.fel.zan.movielibrary.data.repository

sealed class ApiCallResult<out T> {
    data object Loading : ApiCallResult<Nothing>()
    data class Success<T>(val data: T) : ApiCallResult<T>()
    data class Error(
        val exception: Throwable? = null,
        val message: String? = null
    ) : ApiCallResult<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(exception: Throwable? = null, message: String? = null) = Error(exception, message)
        fun loading() = Loading
    }
}