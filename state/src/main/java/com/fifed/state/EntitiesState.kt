package com.fifed.state

import com.fifed.model.RepositoryItemModel
import com.fifed.model.RepositoryOwnerModel

data class EntitiesState(
    val repositories: Map<Int, RepositoryItemModel> = emptyMap(),
    val owners: Map<Int, RepositoryOwnerModel> = emptyMap(),
)