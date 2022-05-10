package com.fifed.data.di.modules

import com.fifed.data.mapper.RepositoriesAPIMapper
import com.fifed.data.mapper.impl.RepositoriesAPIMapperImpl
import org.koin.dsl.module

internal val mappersModule = module {
    factory<RepositoriesAPIMapper> { RepositoriesAPIMapperImpl() }
}