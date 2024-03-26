package com.ckc.kotlincoroutine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckc.kotlincoroutine.DogItem
import com.ckc.kotlincoroutine.RetrofitInstance
import com.ckc.kotlincoroutine.repository.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class DogViewModel: ViewModel() {
    // The backing property is private because it should not be modified outside of the ViewModel.
    private val _dogImageUrl = MutableStateFlow<String?>(null)

    // Exposed as a read-only StateFlow.
    val dogImageUrl: StateFlow<String?> = _dogImageUrl.asStateFlow()

    init {
        fetchRandomDogImage()
    }

    private fun fetchRandomDogImage() {
        viewModelScope.launch {
            try {
                // Assuming getRandomDogImage() returns a Response<DogItem>
                val response: Response<DogItem> = RetrofitInstance.api.getRandomDogImage()
                if (response.isSuccessful) {
                    // Update the StateFlow value with the new image URL
                    _dogImageUrl.value = response.body()?.message
                } else {
                    // Log error or handle the failed response case
                    println("API call unsuccessful: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // Handle exceptions from the API call
                println("Error fetching dog image: ${e.message}")
            }
        }
    }
}
