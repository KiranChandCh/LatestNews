package com.ckc.latestnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ckc.latestnews.repository.NewsRepository
import com.ckc.latestnews.viewmodel.NewsViewModel

class TestNewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
