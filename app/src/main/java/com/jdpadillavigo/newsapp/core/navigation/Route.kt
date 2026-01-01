package com.jdpadillavigo.newsapp.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {
    @Serializable
    data object NewDetail: Route

    @Serializable
    data object Home: Route

    @Serializable
    data object Discover: Route

    @Serializable
    data object Bookmark: Route

    @Serializable
    data object Profile: Route
}