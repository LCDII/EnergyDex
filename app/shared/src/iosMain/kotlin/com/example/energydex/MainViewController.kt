package com.example.energydex

import androidx.compose.ui.window.ComposeUIViewController
import com.example.energydex.app.App
import com.example.energydex.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }