package com.example.illusionapp.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.illusionapp.data.HistoryRepository
import com.example.illusionapp.data.local.database.AppDatabase
import com.example.illusionapp.data.local.entity.History
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HistoryRepository
    val allHistory: LiveData<List<History>>

    init {
        val historyDao = AppDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(historyDao)
        allHistory = repository.allHistory
    }

    fun insert(history: History) = viewModelScope.launch {
        repository.insert(history)
    }

    fun delete(history: History) = viewModelScope.launch {
        repository.delete(history) // Add delete method
    }

    fun getRecentScans(limit: Int): LiveData<List<History>> {
        return repository.getRecentScans(limit)
    }

}