plugins {
    alias(libs.plugins.kotlinMultiplatform)

    id("glossarist-logger")
//    id("glossarist-publish")
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.ksp.api)
                implementation(libs.kotlinpoet.ksp)
            }
        }
    }
}
