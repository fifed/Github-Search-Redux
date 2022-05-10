package com.fifed.data.state_storage.impl

import android.content.Context
import android.util.Log
import com.fifed.data.state_storage.StateStorage
import com.fifed.state.AppState
import com.fifed.tools.utils.FileUtils
import com.google.gson.Gson
import java.io.File

private const val APP_STATE_DIR = "app_state"
private const val APP_STATE_NAME = "app_state.json"

internal class StateStorageImpl(
    private val context: Context,
    private val fileUtils: FileUtils,
    private val gson: Gson
) : StateStorage {

    override suspend fun saveState(state: AppState) {
        val json = gson.toJson(state)
        val stateFile = getStateFile()
        fileUtils.removeFile(stateFile)
        stateFile.writeText(json)
        Log.d("StateStorage", "saved file with size ${stateFile.length() / 1000f}kb")
    }

    override suspend fun getState(): AppState? {
        val stateFile = getStateFile()
        val json = if (stateFile.exists()) stateFile.readText() else ""
        return if (json.isBlank()) {
            null
        } else {
            gson.fromJson(json, AppState::class.java)
        }
    }

    private fun getStateFile(): File {
        val dir = File(context.applicationContext.cacheDir, APP_STATE_DIR)
        if (!dir.exists()) {
            dir.mkdir()
        }
        return File(dir, APP_STATE_NAME)
    }

    override suspend fun clearState() {
        fileUtils.removeFile(getStateFile())
    }
}