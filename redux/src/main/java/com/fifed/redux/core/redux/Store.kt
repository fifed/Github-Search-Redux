package com.fifed.redux.core.redux

import com.fifed.state.AppState
import kotlinx.coroutines.flow.Flow

interface Store {
    val stateFlow: Flow<AppState>
    fun dispatch(action: Action)
}