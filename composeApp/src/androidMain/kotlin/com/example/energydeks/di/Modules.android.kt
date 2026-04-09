package com.example.energydeks.di

import com.example.energydeks.data.energydrink.database.DatabaseDriverFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseDriverFactory(androidApplication()) }
    }