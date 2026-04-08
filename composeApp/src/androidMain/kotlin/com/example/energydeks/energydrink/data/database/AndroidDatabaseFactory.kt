package com.example.energydeks.energydrink.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.EnergyDexDatabase

class AndroidDatabaseFactory(
    private val context: Context
) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            EnergyDexDatabase.Schema,
            context,
            "EnergyDexDatabase.db"
        )
    }
}
