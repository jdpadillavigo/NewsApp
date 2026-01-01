package com.jdpadillavigo.newsapp.news.presentation.new_list

import com.jdpadillavigo.newsapp.news.presentation.models.NewUi

sealed interface NewListAction {
    data class OnNewClick(val newUi: NewUi): NewListAction
    data class OnRetryClick(val load: String): NewListAction
}