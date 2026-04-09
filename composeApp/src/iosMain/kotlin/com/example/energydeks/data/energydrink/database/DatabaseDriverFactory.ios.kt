package com.example.energydeks.data.energydrink.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.EnergyDexDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            EnergyDexDatabase.Schema,
            "EnergyDexDatabase.db"
        )
    }
}