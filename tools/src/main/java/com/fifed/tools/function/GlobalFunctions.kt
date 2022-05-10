package com.fifed.tools.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> runCatchingWithContext(context: CoroutineContext = Dispatchers.IO, call: suspend () -> T): Result<T> {
    return withContext(context) {
        runCatching { call() }
    }
}