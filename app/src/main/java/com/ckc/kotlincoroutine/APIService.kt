package com.ckc.kotlincoroutine

import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): Response<DogItem>
}