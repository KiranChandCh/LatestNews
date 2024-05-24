package com.ckc.latestnews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckc.latestnews.model.NewsItem
import com.ckc.latestnews.model.NewsSectionsRequest
import com.ckc.latestnews.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository): ViewModel() {
    private val _newsBySection = MutableStateFlow<Map<String?, List<NewsItem>>>(emptyMap())
    val newsBySections: StateFlow<Map<String?, List<NewsItem>>> = _newsBySection.asStateFlow()
    private val _newsItem = MutableLiveData<NewsItem>()

    init {
        fetchNewsCategories()
    }

    private fun fetchNewsCategories() = viewModelScope.launch {
        try {
            val response = repository.getNewsCategories()
            if (response != null) {
                val request = NewsSectionsRequest(response)
                getNewsBySections(request)
            } else {
                println("Error: Null response received")
            }
        } catch (e: Exception) {
            println("Error fetching data: ${e.message}")
        }
    }

    private fun getNewsBySections(category: NewsSectionsRequest) {
        viewModelScope.launch {
            try {
                val response = repository.getNewsBySections(category)
                if (response != null) {
                    val newsItems = mutableListOf<NewsItem>()

                    response.keys.forEach { category ->
                        val newsList = response[category] ?: emptyList()
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
                    println("API call unsuccessful}")
                }
            } catch (e: Exception) {
                println("Error fetching sections data: ${e.message}")
            }
        }
    }

    fun setNewsItem(newsItem: NewsItem) {
        _newsItem.value = newsItem
    }

    fun getNewsItem(): NewsItem? {
        return _newsItem.value
    }
}
