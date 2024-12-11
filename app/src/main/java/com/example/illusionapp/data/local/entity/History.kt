package com.example.illusionapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val title: String,
    val timestamp: String,
    val imageUri: String,
    val confidence: Float // New confidence field
)
