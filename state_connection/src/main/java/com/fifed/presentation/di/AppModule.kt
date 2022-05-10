package com.fifed.presentation.di

import com.fifed.presentation.application.lifecycle.AppLifecycleWatcher
import com.fifed.presentation.application.lifecycle.impl.AppLifecycleWatcherImpl
import org.koin.dsl.module

val appModule = module {
    single<AppLifecycleWatcher> { AppLifecycleWatcherImpl(get()) }
}