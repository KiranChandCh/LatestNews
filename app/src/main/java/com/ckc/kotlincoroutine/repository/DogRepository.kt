package com.ckc.kotlincoroutine.repository

import com.ckc.kotlincoroutine.APIService
import com.ckc.kotlincoroutine.DogItem
import retrofit2.Response

class DogRepository(private val apiService: APIService) {
    suspend fun getRandomDogImage(): Response<DogItem> = apiService.getRandomDogImage()
}