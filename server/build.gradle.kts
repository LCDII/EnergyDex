plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
}

group = "dev.lcdii.energydex"
version = "1.0.0"

application { mainClass = "dev.lcdii.energydex.server.ApplicationKt" }

dependencies {
    api(projects.core)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.serverCallId)

    implementation(libs.logback)
    implementation(libs.koin.ktor)
}
