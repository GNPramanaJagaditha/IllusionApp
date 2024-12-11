package com.example.illusionapp.data

import com.example.illusionapp.data.local.ThemePreferences
import kotlinx.coroutines.flow.Flow

class ThemeRepository(private val themePreferences: ThemePreferences) {

    val darkModeFlow: Flow<Boolean> = themePreferences.darkModeFlow

    suspend fun setDarkMode(enabled: Boolean) {
        themePreferences.setDarkMode(enabled)
    }
}
