package com.fifed.redux.middleware

import com.fifed.data.repository.GitHubRepository
import com.fifed.redux.action.repository.LoadRepositoryAction
import com.fifed.redux.action.repository.LoadRepositoryErrorAction
import com.fifed.redux.action.repository.LoadRepositorySuccessAction
import com.fifed.redux.core.redux.Action
import com.fifed.redux.core.redux.Middleware
import com.fifed.state.AppState
import com.fifed.tools.concurrency.AppExecutor

internal class DetailedRepositoryMiddleware(
    private val repository: GitHubRepository,
    private val appExecutor: AppExecutor
) : Middleware {
    override fun handleAction(action: Action, state: AppState, dispatch: (Action) -> Unit): Action {
        when (action) {
            is LoadRepositoryAction -> {
                val repo = state.entitiesState.repositories[action.id]
                val owner = if (repo == null) null else state.entitiesState.owners[repo.ownerId]
                if (repo != null && owner != null) {
                    return LoadRepositorySuccessAction(repo, owner)
                }
                appExecutor.launch {
                    repository.getRepository(action.owner, action.repository)
                        .onSuccess { result -> dispatch(LoadRepositorySuccessAction(result.repository, result.owner)) }
                        .onFailure { error -> dispatch(LoadRepositoryErrorAction(error)) }
                }
            }
        }
        return action
    }
}