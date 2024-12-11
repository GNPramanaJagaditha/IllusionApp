package com.example.illusionapp.data

import androidx.lifecycle.LiveData
import com.example.illusionapp.data.local.dao.HistoryDao
import com.example.illusionapp.data.local.entity.History

class HistoryRepository(private val historyDao: HistoryDao) {

    val allHistory: LiveData<List<History>> = historyDao.getAllHistory()

    suspend fun insert(history: History) {
        historyDao.insert(history)
    }

    suspend fun delete(history: History) { // Add delete method
        historyDao.delete(history)
    }

    fun getRecentScans(limit: Int): LiveData<List<History>> {
        return historyDao.getRecentScans(limit)
    }
}