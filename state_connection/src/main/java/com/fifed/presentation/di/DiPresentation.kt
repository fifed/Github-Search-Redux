package com.fifed.presentation.di

import com.fifed.redux.di.DiRedux
import com.fifed.tools.di.DiTools
import org.koin.java.KoinJavaComponent.getKoin

object DiPresentation {
    private val modules = listOf(systemModule, uiMappersModule, connectorsModule, appModule)
    fun init() {
        getKoin().loadModules(modules)
        DiTools.init()
        DiRedux.init()
    }
}