package com.example.energydeks

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform