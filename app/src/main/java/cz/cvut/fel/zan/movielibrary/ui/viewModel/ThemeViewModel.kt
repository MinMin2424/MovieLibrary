package cz.cvut.fel.zan.movielibrary.ui.viewModel

import android.content.Context
import android.view.View
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.zan.movielibrary.data.dataStore.getThemeMode
import cz.cvut.fel.zan.movielibrary.data.dataStore.saveThemeMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ThemeViewModel(private val context : Context) : ViewModel() {
    private val _isDarkMode = mutableStateOf(true)
    val isDarkMode: State<Boolean> = _isDarkMode
    init {
        viewModelScope.launch {
            val mode = getThemeMode(context).first()
            _isDarkMode.value = mode == "dark"
        }
    }
    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
        viewModelScope.launch {
            saveThemeMode(context, _isDarkMode.value)
        }
    }

    companion object {
        fun provideFactory(context: Context) : ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T: ViewModel> create(modelClass: Class<T>) : T {
                    return ThemeViewModel(context) as T
                }
            }
        }
    }
}