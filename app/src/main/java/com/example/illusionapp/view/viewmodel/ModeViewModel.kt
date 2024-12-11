package com.example.illusionapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.illusionapp.data.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ModeViewModel(private val themeRepository: ThemeRepository) : ViewModel() {

    private val _darkModeState = MutableStateFlow(false)
    val darkModeState: StateFlow<Boolean> get() = _darkModeState

    val darkModeLiveData: LiveData<Boolean> = darkModeState.asLiveData()

    init {
        viewModelScope.launch {
            themeRepository.darkModeFlow.collect { isDarkModeEnabled ->
                _darkModeState.value = isDarkModeEnabled
            }
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            themeRepository.setDarkMode(enabled)
        }
    }
}
