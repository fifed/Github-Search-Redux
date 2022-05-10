package com.fifed.data.state_storage

import com.fifed.state.AppState

interface StateStorage {
    suspend fun saveState(state: AppState)
    suspend fun getState(): AppState?
    suspend fun clearState()
}