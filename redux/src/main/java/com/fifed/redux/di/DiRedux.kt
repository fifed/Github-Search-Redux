package com.fifed.redux.di

import com.fifed.data.di.DiData
import org.koin.java.KoinJavaComponent.getKoin

object DiRedux {
    private val modules = listOf(middlewareModule, reducerModule, storeModule)
    fun init() {
        getKoin().loadModules(modules)
        DiData.init()
    }
}