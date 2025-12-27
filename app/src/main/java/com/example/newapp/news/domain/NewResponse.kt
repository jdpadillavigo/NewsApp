package com.example.newapp.news.domain

data class NewResponse(
    val status: String,
    val totalResults: Int? = null,
    val articles: List<New>? = emptyList(),
    val code: String? = "",
    val message: String? = ""
)