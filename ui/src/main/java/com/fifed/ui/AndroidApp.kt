package com.fifed.ui

import android.app.Application
import com.fifed.presentation.application.lifecycle.AppLifecycleWatcher
import com.fifed.ui.di.DI
import org.koin.android.ext.android.inject

class AndroidApp : Application() {
    private val watcher: AppLifecycleWatcher by inject()
    override fun onCreate() {
        super.onCreate()
        DI.start(applicationContext)
        watcher.init()
    }
}