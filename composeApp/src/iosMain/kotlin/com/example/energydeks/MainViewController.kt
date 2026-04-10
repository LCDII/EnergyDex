package com.example.energydeks

import androidx.compose.ui.window.ComposeUIViewController
import com.example.energydeks.app.App
import com.example.energydeks.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }