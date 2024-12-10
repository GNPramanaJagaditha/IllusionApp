package com.example.illusionapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.illusionapp.data.remote.response.PredictionResponse
import com.example.illusionapp.data.remote.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PredictionViewModel : ViewModel() {

    private val _predictionResult = MutableLiveData<PredictionResponse?>()
    val predictionResult: LiveData<PredictionResponse?> = _predictionResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val api = RetrofitInstance.api

    fun fetchPrediction(file: File) {
        val imagePart = MultipartBody.Part.createFormData(
            "image", file.name, RequestBody.create(MultipartBody.FORM, file)
        )

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true) // Show ProgressBar
            try {
                val response = api.uploadImage(imagePart).execute()
                if (response.isSuccessful) {
                    _predictionResult.postValue(response.body())
                } else {
                    _predictionResult.postValue(null) // Handle API error response
                }
            } catch (e: Exception) {
                _predictionResult.postValue(null) // Handle exceptions
            } finally {
                _isLoading.postValue(false) // Hide ProgressBar
            }
        }
    }
}
