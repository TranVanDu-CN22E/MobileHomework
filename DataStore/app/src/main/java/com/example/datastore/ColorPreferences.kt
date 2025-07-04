package com.example.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("color_prefs")

class ColorPreferences(private val context: Context) {
    companion object {
        val BG_COLOR_KEY = intPreferencesKey("background_color")
    }

    suspend fun saveBackgroundColor(color: Int) {
        context.dataStore.edit { prefs ->
            prefs[BG_COLOR_KEY] = color
        }
    }

    val backgroundColor: Flow<Int> = context.dataStore.data
        .map { prefs -> prefs[BG_COLOR_KEY] ?: 0xFFFFFFFF.toInt() } // mặc định màu trắng
}
