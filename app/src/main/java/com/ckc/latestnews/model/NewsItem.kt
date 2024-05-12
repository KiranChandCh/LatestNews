package com.ckc.latestnews.model

data class NewsItem(
    val link: String? = null,
    val og: String? = null,
    val source: String? = null,
    val sourceIcon: String? = null,
    val title: String? = null,
    val type: String // Or you can use an enum if you prefer
)

