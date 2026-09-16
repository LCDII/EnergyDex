package dev.lcdii.energydex.server.di

import dev.lcdii.energydex.server.config.ServerConfig
import org.koin.core.module.Module
import org.koin.dsl.module

fun serverModule(config: ServerConfig): Module = module {
    single {config}
}