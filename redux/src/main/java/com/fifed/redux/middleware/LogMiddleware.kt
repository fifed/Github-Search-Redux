package com.fifed.redux.middleware

import android.util.Log
import com.fifed.redux.core.redux.Action
import com.fifed.redux.core.redux.Middleware
import com.fifed.state.AppState

internal class LogMiddleware : Middleware {
    override fun handleAction(action: Action, state: AppState, dispatch: (Action) -> Unit): Action {
        Log.d("LogSideEffect", "action : ${action}, \n state: $state")
        return action
    }
}