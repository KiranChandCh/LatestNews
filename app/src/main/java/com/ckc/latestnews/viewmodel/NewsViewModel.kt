package com.ckc.latestnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckc.latestnews.RetrofitInstance
import com.ckc.latestnews.model.NewsDataObject
import com.ckc.latestnews.model.NewsSectionsRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    private val _newsSections = MutableStateFlow<List<String>?>(null)
    private val _newsBySection = MutableStateFlow<NewsDataObject?>(null)

    val newsSections: StateFlow<List<String>?> = _newsSections.asStateFlow()
    val newsBySections: StateFlow<NewsDataObject?> = _newsBySection.asStateFlow()


    init {
        fetchNewsSections()
    }

    private fun fetchNewsSections() = viewModelScope.launch {
        try {
            val response = RetrofitInstance.api.getNewsSections()
            if (response.isSuccessful && response.body() != null) {
                _newsSections.value = response.body()
                val request = NewsSectionsRequest(response.body())
                getNewsBySections(request)
            } else {
                println("API call unsuccessful: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            println("Error fetching dog image: ${e.message}")
        }
    }

    private fun getNewsBySections(category: NewsSectionsRequest) = viewModelScope.launch {
        try {
            val response = RetrofitInstance.api.getNewsBySections(category)
            if (response.isSuccessful && response.body() != null) {
                _newsBySection.value = response.body()
            }
        } catch (e: Exception) {
            println("Error fetching sections data: ${e.message}")
        }
    }

}
