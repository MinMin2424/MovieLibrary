package cz.cvut.fel.zan.movielibrary.ui.viewModel

import androidx.lifecycle.ViewModel
import cz.cvut.fel.zan.movielibrary.data.GetAllMovies
import cz.cvut.fel.zan.movielibrary.data.MovieInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class MovieEditEvent {
    data class AddCommentChanged(val movieId: Int, val comment: String) : MovieEditEvent()
}

class MovieViewModel() : ViewModel() {

    private val _movie = MutableStateFlow(GetAllMovies())
    val movies: StateFlow<List<MovieInfo>> get() = _movie

    fun onEvent(event: MovieEditEvent) {
        when (event) {
            is MovieEditEvent.AddCommentChanged -> {
                addComment(event.movieId, event.comment)
            }
        }
    }

    private fun addComment(movieId: Int, comment: String) {
        _movie.value = _movie.value.map { movie ->
            if (movie.movieId == movieId) {
                movie.addComment(comment)
            } else {
                movie
            }
        }
    }

    fun getMovieById(movieId: Int) : MovieInfo? {
        return _movie.value.find { it.movieId == movieId }
    }
}