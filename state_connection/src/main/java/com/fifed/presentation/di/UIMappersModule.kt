package com.fifed.presentation.di

import com.fifed.presentation.props_mapper.RepositoryUIPropsMapper
import com.fifed.presentation.props_mapper.SearchUIPropsMapper
import com.fifed.presentation.props_mapper.impl.RepositoryUIPropsMapperImpl
import com.fifed.presentation.props_mapper.impl.SearchUIPropsMapperImpl
import org.koin.dsl.module

val uiMappersModule = module {
    factory<SearchUIPropsMapper> { SearchUIPropsMapperImpl(get()) }
    factory<RepositoryUIPropsMapper> { RepositoryUIPropsMapperImpl(get()) }
}