package com.fifed.redux.reducer

import com.fifed.redux.action.search.*
import com.fifed.redux.core.redux.Action
import com.fifed.redux.core.redux.Reducer
import com.fifed.state.AppState
import com.fifed.state.SearchState.Status.NONE

internal class SearchRepositoryReducer : Reducer {
    override fun reduce(action: Action, state: AppState): AppState {
        return with(state) {
            when (action) {
                is SearchScreenInputChangedAction -> {
                    copy(
                        searchState = searchState.copy(
                            input = action.text,
                            status = if (action.text.isBlank()) NONE else searchState.status
                        )
                    )
                }
                is SearchRepositoriesStartedAction -> {
                    copy(
                        searchState = searchState.copy(
                            status = com.fifed.state.SearchState.Status.LOADING,
                            latestQuery = searchState.input
                        )
                    )
                }
                is SearchRepositoriesClearAction -> {
                    copy(
                        searchState = searchState.copy(
                            itemsIds = emptyList()
                        ),
                        entitiesState = entitiesState.copy(
                            repositories = emptyMap(),
                            owners = emptyMap()
                        )
                    )
                }
                is SearchRepositorySuccessAction -> {
                    copy(
                        searchState = searchState.copy(
                            itemsIds = searchState.itemsIds + action.items.map { it.id },
                            loadedPages = action.loadedPage,
                            status = com.fifed.state.SearchState.Status.SUCCESS,
                        ),
                        entitiesState = entitiesState.copy(
                            repositories = entitiesState.repositories + action.items.associateBy { it.id },
                            owners = entitiesState.owners + action.owners.associateBy { it.id },
                        )
                    )
                }
                is SearchRepositoryErrorAction -> {
                    copy(
                        searchState = searchState.copy(
                            status = com.fifed.state.SearchState.Status.ERROR
                        )
                    )
                }

                else -> state
            }
        }
    }
}