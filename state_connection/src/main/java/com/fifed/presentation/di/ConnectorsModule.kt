package com.fifed.presentation.di

import com.fifed.presentation.connector.MainActivityConnector
import com.fifed.presentation.connector.RepositoryScreenConnector
import com.fifed.presentation.connector.SearchScreenConnector
import com.fifed.presentation.connector.impl.MainActivityConnectorImpl
import com.fifed.presentation.connector.impl.RepositoryScreenConnectorImpl
import com.fifed.presentation.connector.impl.SearchScreenConnectorImpl
import org.koin.dsl.module

val connectorsModule = module {
    factory<SearchScreenConnector> { SearchScreenConnectorImpl(get(), get()) }
    factory<RepositoryScreenConnector> { RepositoryScreenConnectorImpl(get(), get()) }
    factory<MainActivityConnector> { MainActivityConnectorImpl(get()) }
}