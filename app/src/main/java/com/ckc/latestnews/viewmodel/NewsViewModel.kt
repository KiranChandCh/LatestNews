package com.ckc.latestnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckc.latestnews.RetrofitInstance
import com.ckc.latestnews.model.NewsDataObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    private val _newsData = MutableStateFlow<NewsDataObject?>(null)
    val newsData: StateFlow<NewsDataObject?> = _newsData.asStateFlow()

    init {
        fetchNewsData()
    }

    private fun fetchNewsData() = viewModelScope.launch {
        try {
            val response = RetrofitInstance.api.getNewsData()
            if (response.isSuccessful && response.body() != null) {
                _newsData.value = response.body()
            } else {
                println("API call unsuccessful: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            println("Error fetching dog image: ${e.message}")
        }
    }
}
