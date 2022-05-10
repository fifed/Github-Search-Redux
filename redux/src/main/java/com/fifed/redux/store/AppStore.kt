package com.fifed.redux.store

import com.fifed.redux.core.redux.Action
import com.fifed.redux.core.redux.Middleware
import com.fifed.redux.core.redux.Reducer
import com.fifed.redux.core.redux.Store
import com.fifed.state.AppState
import com.fifed.tools.concurrency.AppExecutor
import kotlinx.coroutines.flow.MutableStateFlow

internal class AppStore(
    private val reducers: Collection<Reducer>,
    private val middlewares: Collection<Middleware>,
    private val appExecutor: AppExecutor
) : Store {

    override val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState())
    private val state: AppState
        get() = stateFlow.value

    override fun dispatch(action: Action) {
        appExecutor.async {
            val newAction = applyMiddlewares(action, state)
            val newState = applyReducers(newAction, state)
            stateFlow.value = newState
        }
    }

    private fun applyMiddlewares(action: Action, state: AppState): Action {
        return middlewares.fold(action) { actionNext, middleware ->
            middleware.handleAction(action = actionNext, state = state, dispatch = ::dispatch)
        }
    }

    private fun applyReducers(action: Action, state: AppState): AppState {
        return reducers.fold(state) { newState, reducer -> reducer.reduce(action = action, state = newState) }
    }
}