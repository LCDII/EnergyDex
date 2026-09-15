package dev.lcdii.energydex.di

import dev.lcdii.energydex.database.EnergyDexDatabase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    database: EnergyDexDatabase? = null,
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(
            databaseModule(database),
            repositoryModule,
            useCaseModule,
            viewModelModule,
            platformModule
        )
    }
}
