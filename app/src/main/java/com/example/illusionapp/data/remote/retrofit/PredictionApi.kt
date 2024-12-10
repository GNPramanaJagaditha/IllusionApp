package com.example.illusionapp.data.remote.retrofit

import com.example.illusionapp.data.remote.response.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PredictionApi {

    @Multipart
    @POST("predict")
    fun uploadImage(
        @Part image: MultipartBody.Part // Part for the image file
    ): Call<PredictionResponse>
}