package com.jdpadillavigo.newsapp.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.jdpadillavigo.newsapp.news.presentation.bookmark.BookmarkScreen
import com.jdpadillavigo.newsapp.news.presentation.discover.DiscoverScreen
import com.jdpadillavigo.newsapp.news.presentation.home.HomeScreen
import com.jdpadillavigo.newsapp.news.presentation.new_detail.NewDetailScreen
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListAction
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListViewModel
import com.jdpadillavigo.newsapp.news.presentation.profile.ProfileScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    viewModel: NewListViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navigationState = rememberNavigationState(
        startRoute = Route.Home,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    val currentStack = navigationState.backStacks[navigationState.topLevelRoute]
    val currentRoute = currentStack?.lastOrNull() ?: navigationState.topLevelRoute
    val shouldShowBars = currentRoute != Route.NewDetail

    Scaffold(
        modifier = modifier,
        topBar = {
            if (shouldShowBars) {
                TopNavBar(
                    currentRoute = navigationState.topLevelRoute
                )
            }
        },
        bottomBar = {
            if (shouldShowBars) {
                BottomNavBar(
                    selectedKey = navigationState.topLevelRoute,
                    onSelectKey = {
                        navigator.navigate(it)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = navigator::goBack,
            transitionSpec = {
                fadeIn(animationSpec = tween(500)) togetherWith
                    fadeOut(animationSpec = tween(500))
            },
            popTransitionSpec = {
                slideInHorizontally(animationSpec = tween(500)) { -it } +
                        fadeIn(animationSpec = tween(500)) togetherWith
                        slideOutHorizontally(animationSpec = tween(500)) { it } +
                        fadeOut(animationSpec = tween(500))
            },
            predictivePopTransitionSpec = {
                slideInHorizontally(animationSpec = tween(500)) { -it } +
                        fadeIn(animationSpec = tween(500)) togetherWith
                        slideOutHorizontally(animationSpec = tween(500)) { it } +
                        fadeOut(animationSpec = tween(500))
            },
            entries = navigationState.toEntries(
                entryProvider {
                    entry<Route.NewDetail> {
                        NewDetailScreen(
                            state = state,
                            onBack = navigator::goBack,
                        )
                    }
                    entry<Route.Home> {
                        HomeScreen(
                            state = state,
                            onAction = { action ->
                                viewModel.onAction(action)
                                if (action is NewListAction.OnNewClick) {
                                    navigator.navigate(Route.NewDetail)
                                }
                            },
                            loadNews = "top-headlines"
                        )
                    }
                    entry<Route.Discover> {
                        DiscoverScreen(
                            state = state,
                            onAction = { action ->
                                viewModel.onAction(action)
                                if (action is NewListAction.OnNewClick) {
                                    navigator.navigate(Route.NewDetail)
                                }
                            },
                            loadNews = "everything"
                        )
                    }
                    entry<Route.Bookmark> {
                        BookmarkScreen()
                    }
                    entry<Route.Profile> {
                        ProfileScreen()
                    }
                }
            )
        )
    }
}