package com.example.energydex.di

import com.example.energydex.data.energydrink.database.DatabaseDriverFactory
import com.example.energydex.data.energydrink.image.AndroidImageStorage
import com.example.energydex.data.energydrink.image.ImageStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseDriverFactory(androidApplication()) }
        single<ImageStorage> { AndroidImageStorage(androidApplication()) }
    }
