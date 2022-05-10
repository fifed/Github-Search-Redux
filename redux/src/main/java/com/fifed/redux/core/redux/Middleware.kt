package com.fifed.redux.core.redux

import com.fifed.state.AppState

internal interface Middleware {
    fun handleAction(action: Action, state: AppState, dispatch: (Action) -> Unit): Action
}