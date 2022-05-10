package com.fifed.presentation.di

import android.content.res.Resources
import com.fifed.presentation.resource.ResourceManager
import com.fifed.presentation.resource.impl.ResourceManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val systemModule = module {
    factory<Resources> { androidContext().resources }
    factory<ResourceManager> { ResourceManagerImpl(get()) }
}