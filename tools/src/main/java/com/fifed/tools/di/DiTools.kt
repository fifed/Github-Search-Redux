package com.fifed.tools.di

import org.koin.java.KoinJavaComponent.getKoin

object DiTools {
    private val modules = listOf(toolsModule, utilsModule)
    fun init() {
        getKoin().loadModules(modules)
    }
}