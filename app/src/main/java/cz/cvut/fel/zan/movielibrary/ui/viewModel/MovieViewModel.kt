package cz.cvut.fel.zan.movielibrary.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.zan.movielibrary.AppContainer
import cz.cvut.fel.zan.movielibrary.data.datasource.MovieRemoteDataSource
import cz.cvut.fel.zan.movielibrary.data.local.Genre
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.data.remote.RetrofitClient
import cz.cvut.fel.zan.movielibrary.data.repository.ApiCallResult
import cz.cvut.fel.zan.movielibrary.data.repository.MovieRepository
import cz.cvut.fel.zan.movielibrary.data.repository.MovieResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class MovieEditEvent {
    data class AddCommentChanged(val localId: Int, val comment: String) : MovieEditEvent()
    data class InsertMovie(val localId: MovieInfo) : MovieEditEvent()
    data class DeleteMovie(val localId: Int) : MovieEditEvent()
}

class MovieViewModel() : ViewModel() {

    private val movieRepository = MovieRepository(
        movieDbDataSource = AppContainer.movieDbDataSource,
        movieRemoteDataSource = MovieRemoteDataSource(RetrofitClient.omdbApi)
    )

    val movies : StateFlow<List<MovieInfo>> = movieRepository.getAllMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _currentMovie = MutableStateFlow<MovieInfo?>(null)
    val currentMovie: StateFlow<MovieInfo?> = _currentMovie

    suspend fun loadMovieById(localId: Int) {
        val movie = movieRepository.getMovieById(localId)
        _currentMovie.value = movie
    }

    fun onEvent(event: MovieEditEvent) {
        when (event) {
            is MovieEditEvent.AddCommentChanged -> {
                viewModelScope.launch {
                    movieRepository.addComment(event.localId, event.comment)
                    loadMovieById(event.localId)
                }
            }
            is MovieEditEvent.InsertMovie -> {
                viewModelScope.launch {
                    movieRepository.insertMovie(event.localId)
                }
            }
            is MovieEditEvent.DeleteMovie -> {
                viewModelScope.launch {
                    movieRepository.deleteMovieById(event.localId)
                }
            }
        }
    }

    private val _movieResult = MutableStateFlow<ApiCallResult<MovieInfo>?>(null)
    val movieResult: StateFlow<ApiCallResult<MovieInfo>?> = _movieResult.asStateFlow()

    fun fetchAndInsertMovieByTitle(
        title: String,
        onResult: (MovieResult) -> Unit
    ) {
        viewModelScope.launch {
            val result = movieRepository.fetchAndStoreMovieFromWeb(title)
            onResult(result)
        }
    }

    /*fun fetchMovieFromWeb(title: String) {
        viewModelScope.launch {
            _movieResult.value = ApiCallResult.Loading
            try {
                _movieResult.value = movieRepository.fetchAndStoreMovieFromWeb(title)
            } catch (e: Exception) {
                _movieResult.value = ApiCallResult.error(e)
            }
        }
    }

    fun fetchMovieById(imdbId: String) {
        viewModelScope.launch {
            _movieResult.value = ApiCallResult.Loading
            try {
                _movieResult.value = movieRepository.fetchAndStoreMovieById(imdbId)
            } catch (e: Exception) {
                _movieResult.value = ApiCallResult.error(e)
            }
        }
    }*/
}