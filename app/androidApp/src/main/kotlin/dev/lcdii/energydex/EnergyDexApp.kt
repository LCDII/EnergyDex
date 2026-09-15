package dev.lcdii.energydex

import android.app.Application
import dev.lcdii.energydex.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class EnergyDexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@EnergyDexApp)
        }
    }
}