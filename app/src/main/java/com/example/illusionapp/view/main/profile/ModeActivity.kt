package com.example.illusionapp.view.main.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.illusionapp.R
import com.example.illusionapp.data.ThemeRepository
import com.example.illusionapp.data.local.ThemePreferences
import com.example.illusionapp.view.viewmodel.ModeViewModelFactory
import com.example.illusionapp.viewmodel.ModeViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class ModeActivity : AppCompatActivity() {

    private val viewModel: ModeViewModel by viewModels {
        val themePreferences = ThemePreferences(applicationContext)
        val repository = ThemeRepository(themePreferences)
        ModeViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)

        val darkModeSwitch: SwitchMaterial = findViewById(R.id.switch_dark_mode)

        viewModel.darkModeLiveData.observe(this) { isDarkModeEnabled ->
            darkModeSwitch.isChecked = isDarkModeEnabled
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleDarkMode(isChecked)
        }
    }
}
