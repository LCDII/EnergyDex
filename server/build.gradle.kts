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
    implementation(libs.ktor.serverCors)
    implementation(libs.ktor.serverStatusPages)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serverContentNegotiation)


    implementation(libs.logback)
    implementation(libs.koin.ktor)
}
