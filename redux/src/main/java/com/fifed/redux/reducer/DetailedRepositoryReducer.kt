package com.fifed.redux.reducer

import com.fifed.redux.action.repository.LoadRepositoryAction
import com.fifed.redux.action.repository.LoadRepositoryErrorAction
import com.fifed.redux.action.repository.LoadRepositorySuccessAction
import com.fifed.redux.core.redux.Action
import com.fifed.redux.core.redux.Reducer
import com.fifed.state.AppState
import com.fifed.state.RepositoryState.Status.*

internal class DetailedRepositoryReducer : Reducer {
    override fun reduce(action: Action, state: AppState): AppState {
        return with(state) {
            when (action) {
                is LoadRepositoryAction -> {
                    copy(repositoryState = repositoryState.copy(status = LOADING, repositoryId = null, ownerId = null))
                }
                is LoadRepositorySuccessAction -> {
                    handleLoadSuccess(state, action)
                }
                is LoadRepositoryErrorAction -> {
                    copy(repositoryState = repositoryState.copy(status = ERROR, repositoryId = null, ownerId = null))
                }
                else -> state
            }
        }
    }

    private fun handleLoadSuccess(state: AppState, action: LoadRepositorySuccessAction): AppState {
        return with(state) {
            val repositories = if (entitiesState.repositories.containsKey(action.repository.id)) {
                entitiesState.repositories
            } else {
                entitiesState.repositories + (action.repository.id to action.repository)
            }
            val owners = if (entitiesState.owners.containsKey(action.owner.id)) {
                entitiesState.owners
            } else {
                entitiesState.owners + (action.owner.id to action.owner)
            }
            copy(
                repositoryState = repositoryState.copy(
                    status = SUCCESS,
                    repositoryId = action.repository.id,
                    ownerId = action.owner.id
                ),
                entitiesState = entitiesState.copy(
                    repositories = repositories,
                    owners = owners
                )
            )
        }
    }
}