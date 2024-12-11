package com.example.illusionapp.data.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HistoryRetrofitInstance {
    private const val BASE_URL = "https://ml-model-service-788926358989.asia-southeast2.run.app/"

    val api: HistoryApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HistoryApi::class.java)
    }
}
