package com.example.energydeks.energydrink.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.EnergyDexDatabase

class IosDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            EnergyDexDatabase.Schema,
            "EnergyDexDatabase.db"
        )
    }
}