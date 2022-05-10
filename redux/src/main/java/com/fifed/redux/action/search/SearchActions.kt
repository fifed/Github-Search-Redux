package com.fifed.redux.action.search

import com.fifed.model.RepositoryItemModel
import com.fifed.model.RepositoryOwnerModel
import com.fifed.redux.core.redux.Action

object SearchRepositoriesStartedAction : Action
object SearchRepositoriesClearAction : Action
object SearchRepositoriesRetryAction : Action
data class SearchRepositoryErrorAction(val error: Throwable) : Action
data class SearchRepositorySuccessAction(
    val items: List<RepositoryItemModel>,
    val owners: Set<RepositoryOwnerModel>,
    val loadedPage: Int
) : Action

data class SearchScreenInputChangedAction(val text: String) : Action
data class SearchScreenScrollAction(val lastVisiblePosition: Int) : Action