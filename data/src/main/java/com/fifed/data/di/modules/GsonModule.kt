package com.fifed.data.di.modules

import com.fifed.data.state_storage.serialization.GsonProvider
import org.koin.dsl.module

internal val gsonModule = module {
    single { GsonProvider.provide() }
}