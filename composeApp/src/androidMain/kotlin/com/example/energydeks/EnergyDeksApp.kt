package com.example.energydeks

import android.app.Application
import com.example.energydeks.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

//class EnergyDeksApp : Application() {
//    override fun onCreate() {
//        super.onCreate()
//        initKoin {
//            androidLogger()
//            androidContext(this@EnergyDeksApp)
//        }
//    }
//}