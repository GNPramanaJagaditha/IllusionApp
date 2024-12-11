package com.example.illusionapp.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.illusionapp.data.HistoryRepository
import com.example.illusionapp.data.local.database.AppDatabase
import com.example.illusionapp.data.local.entity.History
import com.example.illusionapp.data.remote.retrofit.HistoryRetrofitInstance
import com.example.illusionapp.data.remote.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HistoryRepository
    val allHistory: LiveData<List<History>>

    init {
        val historyDao = AppDatabase.getDatabase(application).historyDao()
        val api = HistoryRetrofitInstance.api
        repository = HistoryRepository(historyDao, api)
        allHistory = repository.allHistory
    }

    fun fetchAndStoreHistory() = viewModelScope.launch {
        repository.fetchAndStoreHistory()
    }

    fun insert(history: History) = viewModelScope.launch {
        repository.insert(history)
    }

    fun delete(history: History) = viewModelScope.launch {
        repository.delete(history)
    }

    fun getRecentScans(limit: Int): LiveData<List<History>> {
        return repository.getRecentScans(limit)
    }
}

