package com.ckc.kotlincoroutine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckc.kotlincoroutine.RetrofitInstance
import com.ckc.kotlincoroutine.repository.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel: ViewModel() {
    private val repository = DogRepository(RetrofitInstance.api)
    private val _dogImageUrl = MutableStateFlow<String?>(null)
    val dogImageUrl: StateFlow<String?> = _dogImageUrl

    init {
        fetchRandomDogImage()
    }

    private fun fetchRandomDogImage() {
        viewModelScope.launch {
            try {
                val response = repository.getRandomDogImage()
                _dogImageUrl.value = response.message
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
