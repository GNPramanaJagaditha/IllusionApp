package com.example.illusionapp.data.remote.response

data class HistoryResponse(
    val data: List<ApiHistory>,
    val message: String,
    val status: String
)

data class ApiHistory(
    val confidence: Float,
    val id: String,
    val image_name: String,
    val predicted_label: String
)

