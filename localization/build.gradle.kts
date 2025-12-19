@file:OptIn(ExperimentalWasmDsl::class)

import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import java.io.FileInputStream
import java.net.URI
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinxSerialization)
    id("glossarist-feature")
    id("glossarist-publish")
}

kotlin {

    androidLibrary {
        namespace = "io.github.lazarusmugo.glossarist.localization"
        compileSdk =
            libs.versions.android.compileSdk
                .get()
                .toInt()
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.multiplatform.settings.no.arg)
        }
    }
}
