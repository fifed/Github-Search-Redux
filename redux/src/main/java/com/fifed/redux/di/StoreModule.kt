package com.fifed.redux.di

import com.fifed.redux.core.redux.Store
import com.fifed.redux.store.AppStore
import org.koin.dsl.module

internal val storeModule = module {
    single<Store> {
        AppStore(
            reducers = get(reducersQualifier),
            middlewares = get(middlewaresQualifier),
            appExecutor = get()
        )
    }
}
