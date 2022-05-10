package com.fifed.tools.concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.Job

interface AppExecutor {
    val coroutineDispatcher: ExecutorCoroutineDispatcher
    fun async(run: () -> Unit)
    fun launch(block: suspend CoroutineScope.() -> Unit): Job
}