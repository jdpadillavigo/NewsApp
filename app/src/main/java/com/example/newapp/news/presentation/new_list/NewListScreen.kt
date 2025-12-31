package com.example.newapp.news.presentation.new_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.newapp.news.presentation.new_list.components.NewListItem
import com.example.newapp.news.presentation.new_list.components.previewNew
import com.example.newapp.ui.theme.NewAppTheme

@Composable
fun NewListScreen(
    state: NewListState,
    onAction: (NewListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(state.news) { newUi ->
            NewListItem(
                newUi = newUi,
                onClick = {
                    onAction(NewListAction.OnNewClick(newUi))
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun NewListScreenPreview() {
    NewAppTheme {
        NewListScreen(
            state = NewListState(
                news = (1..100).map {
                    previewNew
                }
            ),
            onAction = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        )
    }
}
