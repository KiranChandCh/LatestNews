package com.ckc.latestnews.repository

import com.ckc.latestnews.model.NewsSectionsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("v1/news-section-names")
    suspend fun getNewsSections(): Response<List<String>>

    @POST("v1/news-section")
    suspend fun getNewsBySections(@Body request: NewsSectionsRequest): Response<Map<String, List<Map<String, String>>>>

}