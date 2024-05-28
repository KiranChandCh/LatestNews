package com.ckc.latestnews.repository

import com.ckc.latestnews.model.NewsSectionsRequest

interface NewsRepository {
    suspend fun getNewsCategories(): List<String>?
    suspend fun getNewsBySections(request: NewsSectionsRequest): Map<String, List<Map<String, String>>>?
}