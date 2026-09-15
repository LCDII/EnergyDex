package dev.lcdii.energydex.di

import dev.lcdii.energydex.data.energydrink.database.DatabaseDriverFactory
import dev.lcdii.energydex.data.energydrink.image.IosImageStorage
import dev.lcdii.energydex.data.energydrink.image.ImageStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseDriverFactory() }
        single<ImageStorage> { IosImageStorage() }
    }
