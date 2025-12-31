package com.example.newapp.news.presentation.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.news.presentation.home.ScreenGradient
import com.example.newapp.news.presentation.home.components.EmptyScreen
import com.example.newapp.news.presentation.home.components.ErrorScreen
import com.example.newapp.news.presentation.home.components.LoadingScreen
import com.example.newapp.news.presentation.new_list.NewListAction
import com.example.newapp.news.presentation.new_list.NewListScreen
import com.example.newapp.news.presentation.new_list.NewListState
import com.example.newapp.news.presentation.new_list.components.previewNew
import com.example.newapp.ui.theme.NewAppTheme

@Composable
fun DiscoverScreen(
    state: NewListState,
    onAction: (NewListAction) -> Unit,
    loadNews: String,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(loadNews) {
        onAction(NewListAction.OnRetryClick(loadNews))
    }

    if(state.isLoading) {
        LoadingScreen(modifier = modifier)
    } else if(state.errorMessage != null) {
        ErrorScreen(
            state = state,
            onAction = onAction,
            loadNews = loadNews,
            modifier = modifier
        )
    } else if(state.news.isEmpty()) {
        EmptyScreen(modifier = modifier)
    } else {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Column {
                    Text(
                        text = "Discover",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                    Text(
                        text = "News from all around the world",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = "Search bar",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                NewListScreen(
                    state = state,
                    onAction = onAction
                )
            }
            ScreenGradient(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@PreviewLightDark
@Composable
private fun DiscoverScreenPreview() {
    NewAppTheme {
        DiscoverScreen(
            state = NewListState(
                news = (1..10).map {
                    previewNew
                }
            ),
            onAction = {},
            loadNews = "everything"
        )
    }
}