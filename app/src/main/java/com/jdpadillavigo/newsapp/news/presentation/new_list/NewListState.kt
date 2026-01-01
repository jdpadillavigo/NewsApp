package com.jdpadillavigo.newsapp.news.presentation.new_list

import androidx.compose.runtime.Immutable
import com.jdpadillavigo.newsapp.news.presentation.models.NewUi

@Immutable
data class NewListState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val news: List<NewUi> = emptyList(),
    val selectedNew: NewUi? = null
)