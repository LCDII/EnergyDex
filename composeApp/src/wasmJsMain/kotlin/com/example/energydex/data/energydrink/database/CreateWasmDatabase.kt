package com.example.energydex.data.energydrink.database

import app.cash.sqldelight.driver.worker.createDefaultWebWorkerDriver
import com.example.EnergyDexDatabase

suspend fun createWasmDatabase(): EnergyDexDatabase {
    val driver = createDefaultWebWorkerDriver()
    EnergyDexDatabase.Schema.create(driver).await()
    return EnergyDexDatabase(driver)
}
