package com.fifed.state

data class AppState(
    val searchState: SearchState = SearchState(),
    val repositoryState: RepositoryState = RepositoryState(),
    val entitiesState: EntitiesState = EntitiesState()
)
