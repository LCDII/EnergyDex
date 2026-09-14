package com.example.energydex

import androidx.compose.ui.window.ComposeViewport
import com.example.energydex.app.App
import com.example.energydex.data.energydrink.database.createWasmDatabase
import com.example.energydex.di.initKoin
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
