package cz.cvut.fel.zan.movielibrary.ui.viewModel

import androidx.lifecycle.ViewModel
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.data.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/* TODO onEvent method */
sealed class ProfileScreenEditEvent {
    data class UserInfoChanged(val newName: String, val newEmail: String) : ProfileScreenEditEvent()
    data class AddToFavoritesChanged(val movie: MovieInfo) : ProfileScreenEditEvent()
    data class RemoveFromFavoritesChanged(val movie: MovieInfo) : ProfileScreenEditEvent()
    data class AddCommentChanged(val count: Int) : ProfileScreenEditEvent()
}

class UserViewModel() : ViewModel() {

    private val _userInfo = MutableStateFlow(
        UserInfo(
            name = "MinMin Tranova",
            email = "goldenmaknae2424@gmail.com",
            profileImage = R.drawable.user_profile,
            registrationDate = "04.03.2025",
            favoriteMoviesCount = 0,
            commentsCount = 0,
            listFavoriteMovies = emptyList()
        )
    )
    val userInfo: StateFlow<UserInfo> get() = _userInfo

    fun onEvent(event: ProfileScreenEditEvent) {
        when (event) {
            is ProfileScreenEditEvent.UserInfoChanged -> {
                editUserInfo(event.newName, event.newEmail)
            }
            is ProfileScreenEditEvent.AddToFavoritesChanged -> {
                addToFavorites(event.movie)
            }
            is ProfileScreenEditEvent.RemoveFromFavoritesChanged -> {
                removeFromFavorites(event.movie)
            }
            is ProfileScreenEditEvent.AddCommentChanged -> {
                addComment()
            }
        }
    }

    private fun editUserInfo(newName: String, newEmail: String) {
        _userInfo.value = _userInfo.value.copy(name = newName, email = newEmail)
    }

    private fun addToFavorites(movie: MovieInfo) {
        val currentFavorites = _userInfo.value.listFavoriteMovies
        if (currentFavorites.none { it.movieId == movie.movieId }) {
            _userInfo.value = _userInfo.value.copy(
                listFavoriteMovies = currentFavorites + movie,
                favoriteMoviesCount = _userInfo.value.favoriteMoviesCount + 1
            )
        }
    }

    private fun removeFromFavorites(movie: MovieInfo) {
        val updateFavorites = _userInfo.value.listFavoriteMovies.filter { it.movieId != movie.movieId }
        _userInfo.value = _userInfo.value.copy(
            listFavoriteMovies = updateFavorites,
            favoriteMoviesCount = _userInfo.value.favoriteMoviesCount - 1
        )
    }

    private fun addComment() {
        _userInfo.value = _userInfo.value.copy(
            commentsCount = _userInfo.value.commentsCount + 1
        )
    }
}