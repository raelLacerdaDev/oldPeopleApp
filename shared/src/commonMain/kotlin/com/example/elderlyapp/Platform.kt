package com.example.elderlyapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform