package dev.lcdii.energydex.data.energydrink.database

import app.cash.sqldelight.driver.worker.createDefaultWebWorkerDriver
import dev.lcdii.energydex.database.EnergyDexDatabase

suspend fun createWasmDatabase(): EnergyDexDatabase {
    val driver = createDefaultWebWorkerDriver()
    EnergyDexDatabase.Schema.create(driver).await()
    return EnergyDexDatabase(driver)
}
