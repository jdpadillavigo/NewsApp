package com.example.newapp.news.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewResponseDto(
    val status: String,
    val totalResults: Int? = null,
    val articles: List<NewDto>? = emptyList(),
    val code: String? = "",
    val message: String? = ""
)
