package com.ckc.latestnews

import com.ckc.latestnews.model.NewsDataObject
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("v1/cors/news-feed")
    suspend fun getNewsData(): Response<NewsDataObject>
}