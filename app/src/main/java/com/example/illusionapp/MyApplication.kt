package com.example.illusionapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.illusionapp.data.local.ThemePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        val themePreferences = ThemePreferences(this)

        applicationScope.launch {
            val isDarkModeEnabled = themePreferences.darkModeFlow.first()
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}
