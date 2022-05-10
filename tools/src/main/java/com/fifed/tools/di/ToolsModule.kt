package com.fifed.tools.di

import com.fifed.tools.concurrency.AppExecutor
import com.fifed.tools.concurrency.AppThreadExecutor
import org.koin.dsl.module

internal val toolsModule = module {
    single<AppExecutor> { AppThreadExecutor }
}