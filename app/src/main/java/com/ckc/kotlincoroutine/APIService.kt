package com.ckc.kotlincoroutine

import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("breeds/image/random")
    fun getRandomDogImage(): DogItem
}