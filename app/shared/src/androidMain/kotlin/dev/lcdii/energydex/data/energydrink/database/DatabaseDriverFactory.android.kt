package dev.lcdii.energydex.data.energydrink.database

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.lcdii.energydex.database.EnergyDexDatabase

actual class DatabaseDriverFactory (
    private val context: Context
){
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            EnergyDexDatabase.Companion.Schema.synchronous(),
            context,
            "EnergyDexDatabase.db"
        )
    }
}
