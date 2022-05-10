package com.fifed.tools.extension

import com.fifed.tools.concurrency.AppExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.java.KoinJavaComponent.getKoin

fun <T> Flow<T>.flowOnAppThread(): Flow<T> {
    return flowOn(getKoin().get<AppExecutor>().coroutineDispatcher)
}

fun <T> Flow<T>.flowOnMainThread(): Flow<T> {
    return flowOn(Dispatchers.Main)
}