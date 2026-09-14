@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.app.shared)
            implementation(libs.compose.ui)
        }

        wasmJsMain.dependencies {
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        }

    }
}
