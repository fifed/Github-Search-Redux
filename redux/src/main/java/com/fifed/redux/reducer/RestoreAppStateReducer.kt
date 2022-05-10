package com.fifed.redux.reducer

import com.fifed.redux.action.service.RestoreStateServiceAction
import com.fifed.redux.core.redux.Action
import com.fifed.redux.core.redux.Reducer
import com.fifed.state.AppState

internal class RestoreAppStateReducer : Reducer {
    override fun reduce(action: Action, state: AppState): AppState {
        return when (action) {
            is RestoreStateServiceAction -> action.state
            else -> state
        }
    }
}