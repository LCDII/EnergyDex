@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs { browser() }

    androidLibrary {
        namespace = "com.example.energydex.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions { jvmTarget = JvmTarget.JVM_11 }
        androidResources { enable = true }
    }

    sourceSets {
        iosMain.dependencies { implementation(libs.sqldelight.ios) }
        androidMain.dependencies {
            implementation(libs.sqldelight.android)
            implementation(libs.koin.android)
        }
        wasmJsMain.dependencies {
            implementation(libs.sqldelight.web.worker)
            implementation(npm("@cashapp/sqldelight-sqljs-worker", libs.versions.sqlDelight.get()))
            implementation(npm("sql.js", "1.13.0"))
        }
        commonMain.dependencies {
            api(projects.core)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqldelight.runtime)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.sqldelight.async.extensions)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.coil.compose)
            implementation(libs.backdrop)
        }
        commonTest.dependencies { implementation(libs.kotlin.test) }
    }
}

dependencies { androidRuntimeClasspath(libs.compose.uiTooling) }

sqldelight {
    databases {
        create("EnergyDexDatabase") {
            packageName.set("com.example")
            generateAsync.set(true)
        }
    }
}
