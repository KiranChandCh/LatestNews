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
    private val _newsSection = MutableStateFlow<List<String>?>(null)
    private val _newsBySection = MutableStateFlow<List<String>?>(null)
    private val _newsData = MutableStateFlow<NewsDataObject?>(null)
    val newsData: StateFlow<NewsDataObject?> = _newsData.asStateFlow()
    val newsBySections: StateFlow<List<String>?> = _newsBySection.asStateFlow()
    val newsSections: StateFlow<List<String>?> = _newsSection.asStateFlow()


    init {
        fetchNewsCategories()
        //fetchNewsData()
    }

    private fun fetchNewsCategories() = viewModelScope.launch {
        try {
            val response = RetrofitInstance.api.getNewsSections()
            if (response.isSuccessful && response.body() != null) {
                val sectionNAmes = response.body()
                val request = NewsSectionsRequest(sectionNAmes)
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
                _newsData.value = response.body()
            }
        } catch (e: Exception) {
            println("Error fetching sections data: ${e.message}")
        }
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
