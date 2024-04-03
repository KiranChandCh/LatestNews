package com.ckc.latestnews.repository

import com.ckc.latestnews.APIService
import com.ckc.latestnews.model.NewsDataObject
import retrofit2.Response

class NewsRepo(private val apiService: APIService) {
    suspend fun getNewsData(): Response<NewsDataObject> = apiService.getNewsData()
}