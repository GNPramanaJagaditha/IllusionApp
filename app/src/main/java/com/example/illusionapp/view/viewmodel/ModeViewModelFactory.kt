package com.example.illusionapp.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.illusionapp.data.ThemeRepository
import com.example.illusionapp.viewmodel.ModeViewModel

class ModeViewModelFactory(private val repository: ThemeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModeViewModel::class.java)) {
            return ModeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
