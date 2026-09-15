package dev.lcdii.energydex

import androidx.compose.ui.window.ComposeViewport
import dev.lcdii.energydex.app.App
import dev.lcdii.energydex.data.energydrink.database.createWasmDatabase
import dev.lcdii.energydex.di.initKoin
import androidx.compose.ui.ExperimentalComposeUiApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    MainScope().launch {
        val database = createWasmDatabase()
        initKoin(database)

        ComposeViewport {
            App()
        }
    }
}
