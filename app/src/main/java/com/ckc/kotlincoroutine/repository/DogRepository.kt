package com.ckc.kotlincoroutine.repository

import com.ckc.kotlincoroutine.APIService
import com.ckc.kotlincoroutine.DogItem

class DogRepository(private val apiService: APIService) {
    fun getRandomDogImage(): DogItem = apiService.getRandomDogImage()
}