plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
}

group = "com.example.energydex"
version = "1.0.0"

application { mainClass = "com.example.energydex.server.ApplicationKt" }

dependencies {
    api(projects.core)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback)
}
