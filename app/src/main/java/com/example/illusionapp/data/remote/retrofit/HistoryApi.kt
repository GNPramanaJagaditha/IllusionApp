package com.example.illusionapp.data.remote.retrofit

import com.example.illusionapp.data.remote.response.HistoryResponse
import retrofit2.http.GET

interface HistoryApi {
    @GET("history")
    suspend fun fetchHistory(): HistoryResponse
}

