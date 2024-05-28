package com.ckc.latestnews

import com.ckc.latestnews.repository.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    /*val moshi = Moshi.Builder() // adapter
        .add(KotlinJsonAdapterFactory())
        .build()*/

    val api: APIService by lazy {
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // Ensure Moshi is set up correctly
            .baseUrl("https://ok.surf/api/") // Correct base URL
            .build()
            .create(APIService::class.java)
    }
}

