package com.ckc.kotlincoroutine

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    // Lazy initialization of the API service
    val api: APIService by lazy {
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/") // Base URL of the API
            .addConverterFactory(MoshiConverterFactory.create()) // Use Moshi for JSON conversion
            .build() // Create the Retrofit instance
            .create(APIService::class.java) // Create the DogApiService implementation
    }
}
