package com.fifed.tools.di

import com.fifed.tools.utils.FileUtils
import com.fifed.tools.utils.impl.FileUtilsImpl
import org.koin.dsl.module

internal val utilsModule = module {
    factory<FileUtils> { FileUtilsImpl(get()) }
}