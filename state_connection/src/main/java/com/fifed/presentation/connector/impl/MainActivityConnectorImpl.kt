package com.fifed.presentation.connector.impl

import com.fifed.presentation.connector.MainActivityConnector
import com.fifed.redux.action.app.AppRestoredAction
import com.fifed.redux.core.redux.Store

internal class MainActivityConnectorImpl(private val store: Store) : MainActivityConnector {
    override fun onCreated(isRestored: Boolean) {
        if (isRestored) {
            store.dispatch(AppRestoredAction)
        }
    }
}