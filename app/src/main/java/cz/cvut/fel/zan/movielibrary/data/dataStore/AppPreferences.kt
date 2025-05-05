package cz.cvut.fel.zan.movielibrary.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

object AppPreferencesKeys {
    val LAST_OPENED_SCREEN = stringPreferencesKey("last_opened_screen")
    val THEME_MODE = stringPreferencesKey("theme_mode")
}

val Context.screenDataStore : DataStore<Preferences> by preferencesDataStore(name = "screen_preferences")

suspend fun saveLastOpenedScreen(context: Context, route: String) {
    context.screenDataStore.edit { preferences ->
        preferences[AppPreferencesKeys.LAST_OPENED_SCREEN] = route
    }
}

fun getLastOpenedScreen(context: Context) = context.screenDataStore.data
    .map { prefs ->
        prefs[AppPreferencesKeys.LAST_OPENED_SCREEN]
    }

suspend fun saveThemeMode(context: Context, isDarkMode: Boolean) {
    context.screenDataStore.edit { preferences ->
        preferences[AppPreferencesKeys.THEME_MODE] = if (isDarkMode) "dark" else "light"
    }
}

fun getThemeMode(context: Context) = context.screenDataStore.data
    .map { prefs ->
        prefs[AppPreferencesKeys.THEME_MODE] ?: "dark"
    }

suspend fun clearPreferences(context: Context) {
    context.screenDataStore.edit { it.clear() }
}