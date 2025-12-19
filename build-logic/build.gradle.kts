plugins { `kotlin-dsl` }

dependencies {
    implementation(libs.plugins.kotlinMultiplatform.toDep())
    implementation(libs.plugins.androidKmpLibrary.toDep())
    implementation(libs.plugins.androidApplication.toDep())
    implementation(libs.plugins.composeCompiler.toDep())
    implementation(libs.plugins.composeMultiplatform.toDep())
    implementation(libs.plugins.ktlint.toDep())
    implementation(libs.plugins.ksp.toDep())
    implementation(libs.plugins.vanniktechMavenPublish.toDep())

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

fun Provider<PluginDependency>.toDep() =
    map {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    }
