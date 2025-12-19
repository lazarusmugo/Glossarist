package com.mugo.glossarist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform