package com.ckc.latestnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckc.latestnews.RetrofitInstance
import com.ckc.latestnews.model.NewsItem
import com.ckc.latestnews.model.NewsSectionsRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    private val _newsBySection = MutableStateFlow<Map<String, List<NewsItem>>>(emptyMap())
    val newsBySections: StateFlow<Map<String, List<NewsItem>>> = _newsBySection.asStateFlow()

    init {
        fetchNewsBySections()
    }

    private fun fetchNewsBySections() = viewModelScope.launch {
        try {
            val response = RetrofitInstance.api.getNewsSections()
            if (response.isSuccessful && response.body() != null) {
                val request = NewsSectionsRequest(response.body())
                getNewsBySections(request)
            } else {
                println("API call unsuccessful: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            println("Error fetching dog image: ${e.message}")
        }
    }

    private fun getNewsBySections(category: NewsSectionsRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getNewsBySections(category)
                if (response.isSuccessful && response.body() != null) {
                    val newsItems = mutableListOf<NewsItem>()

                    response.body()?.keys?.forEach { category ->
                        val newsList = response.body()?.get(category) ?: emptyList()
                        newsList.forEach { newsItem ->
                            newsItems.add(
                                NewsItem(
                                    link = newsItem["link"],
                                    og = newsItem["og"],
                                    source = newsItem["source"],
                                    sourceIcon = newsItem["source_icon"],
                                    title = newsItem["title"],
                                    type = category // Set the type to the category name
                                )
                            )
                        }
                    }
                    val values = newsItems.groupBy { it.type }
                    _newsBySection.value = values
                } else {
                    println("API call unsuccessful: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error fetching sections data: ${e.message}")
            }
        }
    }
}
