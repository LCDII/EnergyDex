package com.example.energydeks.data.energydrink.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.EnergyDexDatabase

actual class DatabaseDriverFactory (
    private val context: Context
){
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            EnergyDexDatabase.Companion.Schema,
            context,
            "EnergyDexDatabase.db"
        )
    }
}