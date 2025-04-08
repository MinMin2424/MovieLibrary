package cz.cvut.fel.zan.movielibrary.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.zan.movielibrary.AppContainer
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class MovieEditEvent {
    data class AddCommentChanged(val movieId: Int, val comment: String) : MovieEditEvent()
    data class InsertMovie(val movieInfo: MovieInfo) : MovieEditEvent()
    data class DeleteMovie(val movieId: Int) : MovieEditEvent()
}

class MovieViewModel() : ViewModel() {

    private val movieRepository = MovieRepository(
        AppContainer.movieDbDataSource
    )

    val movies : StateFlow<List<MovieInfo>> = movieRepository.getAllMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _currentMovie = MutableStateFlow<MovieInfo?>(null)
    val currentMovie: StateFlow<MovieInfo?> = _currentMovie

//    suspend fun loadMovieById(movieId: Int) : MovieInfo {
//        return movieRepository.getMovieById(movieId)
//    }

    suspend fun loadMovieById(movieId: Int) {
        val movie = movieRepository.getMovieById(movieId)
        _currentMovie.value = movie
    }

    fun onEvent(event: MovieEditEvent) {
        when (event) {
            is MovieEditEvent.AddCommentChanged -> {
                viewModelScope.launch {
                    movieRepository.addComment(event.movieId, event.comment)
                    loadMovieById(event.movieId)
                }
            }
            is MovieEditEvent.InsertMovie -> {
                viewModelScope.launch {
                    movieRepository.insertMovie(event.movieInfo)
                }
            }
            is MovieEditEvent.DeleteMovie -> {
                viewModelScope.launch {
                    movieRepository.deleteMovieById(event.movieId)
                }
            }
        }
    }
}