package com.fifed.data.di.modules

import com.fifed.data.repository.GitHubRepository
import com.fifed.data.repository.impl.GitHubRepositoryImpl
import org.koin.dsl.module

internal val repositoriesModule = module {
    factory<GitHubRepository> { GitHubRepositoryImpl(get(), get()) }
}