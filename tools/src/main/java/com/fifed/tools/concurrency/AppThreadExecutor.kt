package com.fifed.tools.concurrency

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadFactory

internal object AppThreadExecutor : AppExecutor {
    private val executorService: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor(AppThreadFactory())
    override val coroutineDispatcher = executorService.asCoroutineDispatcher()
    private val coroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    override fun async(run: () -> Unit) {
        executorService.execute(run)
    }

    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return coroutineScope.launch {
            block()
        }
    }
}

private class AppThreadFactory : ThreadFactory {
    override fun newThread(r: Runnable) = Thread(r, "App execution thread")
}