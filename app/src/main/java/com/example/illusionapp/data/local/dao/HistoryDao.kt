package com.example.illusionapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.illusionapp.data.local.entity.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(historyList: List<History>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: History)

    @Query("SELECT * FROM history_table ORDER BY id DESC")
    fun getAllHistory(): LiveData<List<History>>

    @Delete
    suspend fun delete(history: History)

    @Query("SELECT * FROM history_table ORDER BY id DESC LIMIT :limit")
    fun getRecentScans(limit: Int): LiveData<List<History>>
}
