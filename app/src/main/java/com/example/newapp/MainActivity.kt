package com.example.newapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.newapp.core.navigation.NavigationRoot
import com.example.newapp.news.presentation.new_list.NewListScreen
import com.example.newapp.news.presentation.new_list.NewListState
import com.example.newapp.news.presentation.new_list.NewListViewModel
import com.example.newapp.news.presentation.new_list.previewNew
import com.example.newapp.ui.theme.NewAppTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val viewmodel: NewListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.statusBars
                ) { innerPadding ->
                    val rootModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.surface)
                        .consumeWindowInsets(WindowInsets.navigationBars)

//                    NewListScreen(
//                        state = NewListState(
//                            news = (1..100).map {
//                                previewNew
//                            }
//                        ),
//                        onAction = {},
//                        modifier = rootModifier
//                    )

                    NavigationRoot(
                        modifier = rootModifier,
                        viewModel = viewmodel
                    )
                }
            }
        }
    }
}
