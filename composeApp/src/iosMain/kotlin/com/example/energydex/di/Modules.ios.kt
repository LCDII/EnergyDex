package com.example.energydex.di

import com.example.energydex.data.energydrink.database.DatabaseDriverFactory
import com.example.energydex.data.energydrink.image.IosImageStorage
import com.example.energydex.data.energydrink.image.ImageStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseDriverFactory() }
        single<ImageStorage> { IosImageStorage() }
    }
