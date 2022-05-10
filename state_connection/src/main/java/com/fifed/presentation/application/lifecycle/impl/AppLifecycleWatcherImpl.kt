package com.fifed.presentation.application.lifecycle.impl

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.fifed.presentation.application.lifecycle.AppLifecycleWatcher
import com.fifed.redux.action.app.AppInBackgroundAction
import com.fifed.redux.core.redux.Store

internal class AppLifecycleWatcherImpl(private val store: Store) : AppLifecycleWatcher, DefaultLifecycleObserver {

    override fun init() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        store.dispatch(AppInBackgroundAction)
    }
}