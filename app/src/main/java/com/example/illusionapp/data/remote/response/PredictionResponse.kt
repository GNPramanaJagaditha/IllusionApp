package com.example.illusionapp.data.remote.response

data class PredictionResponse(
    val data: Data,
    val message: String,
    val status: String
)

data class Data(
    val confidence: Float,
    val predicted_label: String
)