package com.fifed.redux.di

import com.fifed.redux.core.redux.Reducer
import com.fifed.redux.reducer.DetailedRepositoryReducer
import com.fifed.redux.reducer.RestoreAppStateReducer
import com.fifed.redux.reducer.SearchRepositoryReducer
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val reducersQualifier = named("reducers")

internal val reducerModule = module {
    factory (reducersQualifier) { collectReducers(get(), get(), get()) }
    factory { SearchRepositoryReducer() }
    factory { DetailedRepositoryReducer() }
    factory { RestoreAppStateReducer() }
}

private fun collectReducers(
    searchRepositoryReducer: SearchRepositoryReducer,
    repositoryReducer: DetailedRepositoryReducer,
    restoreAppStateReducer: RestoreAppStateReducer
): Collection<Reducer> = listOf(
    searchRepositoryReducer,
    repositoryReducer,
    restoreAppStateReducer
)