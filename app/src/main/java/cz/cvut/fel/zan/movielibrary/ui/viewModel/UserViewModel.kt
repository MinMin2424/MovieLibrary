package cz.cvut.fel.zan.movielibrary.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.zan.movielibrary.AppContainer
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.local.UserInfo
import cz.cvut.fel.zan.movielibrary.data.local.getMinMin
import cz.cvut.fel.zan.movielibrary.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/* TODO onEvent method */
sealed class ProfileScreenEditEvent {
    data class UserInfoChanged(val newName: String, val newEmail: String) : ProfileScreenEditEvent()
    data class AddToFavoritesChanged(val movie: MovieInfo) : ProfileScreenEditEvent()
    data class RemoveFromFavoritesChanged(val movie: MovieInfo) : ProfileScreenEditEvent()
    data class AddCommentChanged(val count: Int) : ProfileScreenEditEvent()
}

class UserViewModel() : ViewModel() {

    private val userRepository = UserRepository(
        AppContainer.userDbDataSource
    )

//    val userInfo: StateFlow<UserInfo> = userRepository.getUser()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = getMinMin()
//        )

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    init {
        viewModelScope.launch {
            initializeUser()
            userRepository.getUser().collect { user ->
                _userInfo.value = user
            }
        }
    }

    private suspend fun initializeUser() {
        val count = userRepository.getUserCount()
        if (count == 0) {
            userRepository.insertUser(getMinMin())
        }
    }

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
        viewModelScope.launch {
            _userInfo.value?.let {
                userRepository.updateUser(it.copy(
                    name = newName,
                    email = newEmail
                ))
            }
        }
    }

    private fun addToFavorites(movie: MovieInfo) {
        viewModelScope.launch {
            userRepository.addToFavorites(movie)
        }
    }

    private fun removeFromFavorites(movie: MovieInfo) {
        viewModelScope.launch {
            userRepository.removeFromFavorites(movie)
        }
    }

    private fun addComment() {
        viewModelScope.launch {
            userRepository.addComment()
        }
    }
}