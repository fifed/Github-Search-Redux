package com.fifed.redux.di

import com.fifed.redux.core.redux.Middleware
import com.fifed.redux.middleware.DetailedRepositoryMiddleware
import com.fifed.redux.middleware.LogMiddleware
import com.fifed.redux.middleware.SearchRepositoryMiddleware
import com.fifed.redux.middleware.StoreStateMiddleware
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val middlewaresQualifier = named("middlewares")

internal val middlewareModule = module {
    factory(middlewaresQualifier) { collectMiddlewares(get(), get(), get(), get()) }
    factory { SearchRepositoryMiddleware(get(), get()) }
    factory { DetailedRepositoryMiddleware(get(), get()) }
    factory { StoreStateMiddleware(get(), get()) }
    factory { LogMiddleware() }
}

private fun collectMiddlewares(
    storeStateMiddleware: StoreStateMiddleware,
    searchRepositoryMiddleware: SearchRepositoryMiddleware,
    detailedRepositoryMiddleware: DetailedRepositoryMiddleware,
    logMiddleware: LogMiddleware
): Collection<Middleware> = listOf(
    storeStateMiddleware,
    searchRepositoryMiddleware,
    detailedRepositoryMiddleware,
    logMiddleware
)