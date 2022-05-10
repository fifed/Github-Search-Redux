package com.fifed.model

data class SearchMapResult(val repositories: List<RepositoryItemModel>, val owners: Set<RepositoryOwnerModel>)
data class RepoMapResult(val repository: RepositoryItemModel, val owner: RepositoryOwnerModel)