package com.example.illusionapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.illusionapp.data.local.ThemePreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val themePreferences = ThemePreferences(this)
        GlobalScope.launch {
            val isDarkModeEnabled = themePreferences.darkModeFlow.first()
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}
