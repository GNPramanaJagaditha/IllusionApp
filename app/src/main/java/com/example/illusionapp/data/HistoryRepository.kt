package com.example.illusionapp.data

import androidx.lifecycle.LiveData
import com.example.illusionapp.data.local.dao.HistoryDao
import com.example.illusionapp.data.local.entity.History
import com.example.illusionapp.data.remote.retrofit.HistoryApi

class HistoryRepository(
    private val historyDao: HistoryDao,
    private val api: HistoryApi // Correct type
) {

    val allHistory: LiveData<List<History>> = historyDao.getAllHistory()

    suspend fun fetchAndStoreHistory() {
        try {
            val response = api.fetchHistory()
            if (response.status == "success") {
                val historyList = response.data.map {
                    History(
                        label = it.predicted_label,
                        title = it.image_name,
                        timestamp = System.currentTimeMillis().toString(),
                        confidence = it.confidence,
                        imageUri = "" // Replace with actual URI if available
                    )
                }
                historyDao.insertAll(historyList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insert(history: History) {
        historyDao.insert(history)
    }

    suspend fun delete(history: History) {
        historyDao.delete(history)
    }

    fun getRecentScans(limit: Int): LiveData<List<History>> {
        return historyDao.getRecentScans(limit)
    }
}


