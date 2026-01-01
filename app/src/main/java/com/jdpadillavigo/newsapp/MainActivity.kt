package com.jdpadillavigo.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.ui.Modifier
import com.jdpadillavigo.newsapp.core.navigation.NavigationRoot
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListViewModel
import com.jdpadillavigo.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: NewListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                NavigationRoot(
                    modifier = Modifier
                        .consumeWindowInsets(WindowInsets.navigationBars),
                    viewModel = viewModel
                )
            }
        }
    }
}
