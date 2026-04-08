package com.example.energydeks.energydrink.data.database

import com.example.EnergyDexDatabase

class Database(
    factory: DatabaseDriverFactory
) {
    val database = EnergyDexDatabase(factory.createDriver())
}