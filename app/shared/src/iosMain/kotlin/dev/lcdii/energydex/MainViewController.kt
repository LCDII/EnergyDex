package dev.lcdii.energydex

import androidx.compose.ui.window.ComposeUIViewController
import dev.lcdii.energydex.app.App
import dev.lcdii.energydex.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }