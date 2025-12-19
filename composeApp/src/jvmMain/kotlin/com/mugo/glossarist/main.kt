package com.mugo.glossarist

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Glossarist",
    ) {
        App()
    }
}