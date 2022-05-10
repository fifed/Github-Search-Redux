package com.fifed.ui.di

import android.content.Context
import com.fifed.presentation.di.DiPresentation
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

object DI {
    fun start(context: Context) {
        startKoin {
            androidContext(context)
        }
        DiPresentation.init()
    }
}