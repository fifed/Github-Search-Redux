package com.fifed.redux.action.repository

import com.fifed.model.RepositoryItemModel
import com.fifed.model.RepositoryOwnerModel
import com.fifed.redux.core.redux.Action

data class LoadRepositoryAction(val id: Int, val owner: String, val repository: String) : Action
data class LoadRepositorySuccessAction(val repository: RepositoryItemModel, val owner: RepositoryOwnerModel) : Action
data class LoadRepositoryErrorAction(val error: Throwable) : Action