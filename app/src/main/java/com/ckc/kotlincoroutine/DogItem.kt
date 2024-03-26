package com.ckc.kotlincoroutine

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogItem(
    val message: String,
    val status: String
)
