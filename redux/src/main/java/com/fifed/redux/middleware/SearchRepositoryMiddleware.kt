package com.fifed.redux.middleware

import com.fifed.data.repository.GitHubRepository
import com.fifed.redux.action.search.*
import com.fifed.redux.core.redux.Action
import com.fifed.redux.core.redux.Middleware
import com.fifed.state.AppState
import com.fifed.state.SearchState
import com.fifed.state.SearchState.Status.ERROR
import com.fifed.state.SearchState.Status.LOADING
import com.fifed.tools.concurrency.AppExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

internal class SearchRepositoryMiddleware(
    private val repository: GitHubRepository,
    private val appExecutor: AppExecutor
) : Middleware {
    private var loadingJob: Job? = null
    private var debounceJob: Job? = null
    override fun handleAction(action: Action, state: AppState, dispatch: (Action) -> Unit): Action {
        when (action) {
            is SearchScreenInputChangedAction -> {
                debounceJob?.cancel()
                debounceJob = appExecutor.launch {
                    delay(500)
                    loadRepositories(dispatch, action.text, state.searchState)?.let { newAction ->
                        dispatch(newAction)
                    }
                }
            }
            is SearchScreenScrollAction -> {
                if (action.lastVisiblePosition > state.searchState.itemsIds.size - 5) {
                    loadRepositories(dispatch, state.searchState.input, state.searchState)?.let { newAction ->
                        return newAction
                    }
                }
            }
            is SearchRepositoriesRetryAction -> {
                loadRepositories(dispatch, state.searchState.input, state.searchState, true)?.let { newAction ->
                    return newAction
                }
            }
            else -> {
                //NOP
            }
        }
        return action
    }

    private fun loadRepositories(
        dispatch: (Action) -> Unit,
        text: String,
        state: SearchState,
        retry: Boolean = false
    ): Action? {
        if (!retry && (state.status == LOADING || state.status == ERROR)) {
            return null
        }
        if (text != state.input) {
            dispatch(SearchRepositoriesClearAction)
        }
        loadingJob?.cancel()
        if (text.isBlank()) {
            return null
        }
        loadingJob = appExecutor.launch {
            repository.searchRepositories(searchQuery = text.trim(), page = state.loadedPages + 1, pageSize = 10)
                .onSuccess { result ->
                    dispatch(SearchRepositorySuccessAction(result.repositories, result.owners, state.loadedPages + 1))
                }.onFailure { error ->
                    dispatch(SearchRepositoryErrorAction(error))
                }
        }
        return SearchRepositoriesStartedAction
    }

}