package com.fifed.data.di

import com.fifed.data.di.modules.*
import org.koin.java.KoinJavaComponent.getKoin

object DiData {
    private val modules = listOf(
        networkModule, repositoriesModule, mappersModule, storageModule, gsonModule,
    )

    fun init() {
        getKoin().loadModules(modules)
    }
}