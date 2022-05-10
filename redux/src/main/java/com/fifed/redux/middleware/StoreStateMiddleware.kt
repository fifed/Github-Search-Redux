package com.fifed.redux.middleware

import android.util.Log
import com.fifed.data.state_storage.StateStorage
import com.fifed.redux.action.app.AppInBackgroundAction
import com.fifed.redux.action.app.AppRestoredAction
import com.fifed.redux.action.service.RestoreStateServiceAction
import com.fifed.redux.core.redux.Action
import com.fifed.redux.core.redux.Middleware
import com.fifed.state.AppState
import com.fifed.tools.concurrency.AppExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class StoreStateMiddleware(
    private val storage: StateStorage,
    private val appExecutor: AppExecutor
) : Middleware {

    override fun handleAction(action: Action, state: AppState, dispatch: (Action) -> Unit): Action {
        when (action) {
            AppRestoredAction -> restoreState(dispatch)
            AppInBackgroundAction -> saveState(state)
            else -> {
                //NOP
            }
        }
        return action
    }

    private fun restoreState(dispatch: (Action) -> Unit) {
        appExecutor.launch {
            withContext(Dispatchers.IO) {
                val start = System.currentTimeMillis()
                val restoredState = storage.getState()
                storage.clearState()
                if (restoredState != null) {
                    Log.d("StoreStateMiddleware", "State restored for ${System.currentTimeMillis() - start}ms")
                    dispatch(RestoreStateServiceAction(restoredState))
                } else {
                    Log.d("StoreStateMiddleware", "State can not be restored")
                }
            }
        }
    }

    private fun saveState(state: AppState) {
        appExecutor.launch {
            withContext(Dispatchers.IO) {
                val start = System.currentTimeMillis()
                storage.saveState(state)
                Log.d("StoreStateMiddleware", "State saved for ${System.currentTimeMillis() - start}ms")
            }
        }
    }
}