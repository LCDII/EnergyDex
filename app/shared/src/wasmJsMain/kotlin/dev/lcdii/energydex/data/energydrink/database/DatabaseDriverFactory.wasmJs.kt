package dev.lcdii.energydex.data.energydrink.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.createDefaultWebWorkerDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver = createDefaultWebWorkerDriver()
}
