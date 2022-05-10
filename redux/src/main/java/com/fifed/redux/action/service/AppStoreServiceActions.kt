package com.fifed.redux.action.service

import com.fifed.redux.core.redux.Action
import com.fifed.state.AppState

data class RestoreStateServiceAction(val state: AppState): Action