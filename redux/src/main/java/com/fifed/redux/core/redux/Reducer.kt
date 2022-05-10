package com.fifed.redux.core.redux

import com.fifed.state.AppState

internal interface Reducer {
    fun reduce(action: Action, state: AppState): AppState
}