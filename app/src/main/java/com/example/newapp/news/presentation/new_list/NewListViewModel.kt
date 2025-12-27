package com.example.newapp.news.presentation.new_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newapp.core.data.networking.HttpClientFactory.create
import com.example.newapp.news.data.networking.RemoteNewDataSource
import com.example.newapp.news.presentation.models.NewUi
import com.example.newapp.news.presentation.models.toNewUi
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewListViewModel: ViewModel() {
    private val remoteNewDataSource = RemoteNewDataSource(
        httpClient = create(CIO)
    )

    private val _state = MutableStateFlow(NewListState())
    val state = _state
        .onStart { loadEverything() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            NewListState()
        )

    private fun loadEverything() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            )}

            when {
                remoteNewDataSource.getEverything().status == "ok" -> _state.update { it.copy(
                    isLoading = false,
                    news = remoteNewDataSource.getEverything().articles?.map { it.toNewUi() } ?: emptyList()
                )}
                else -> _state.update { it.copy(
                    isLoading = false
                )}
            }
        }
    }

    private fun loadTopHeadlines() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            )}

            when {
                remoteNewDataSource.getTopHeadlines().status == "ok" -> _state.update { it.copy(
                    isLoading = false,
                    news = remoteNewDataSource.getTopHeadlines().articles?.map { it.toNewUi() } ?: emptyList()
                )}
                else -> _state.update { it.copy(
                    isLoading = false
                )}
            }
        }
    }

    private fun selectNew(newUi: NewUi) {
        _state.update { it.copy(selectedNew = newUi) }
    }

    fun onAction(action: NewListAction) {
        when(action) {
            is NewListAction.OnNewClick -> {
                selectNew(action.newUi)
            }
        }
    }
}