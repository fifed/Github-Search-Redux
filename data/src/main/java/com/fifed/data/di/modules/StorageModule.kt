package com.fifed.data.di.modules

import com.fifed.data.state_storage.StateStorage
import com.fifed.data.state_storage.impl.StateStorageImpl
import org.koin.dsl.module

internal val  storageModule = module {
    single<StateStorage> { StateStorageImpl(get(), get(), get()) }
}