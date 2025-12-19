import org.gradle.accessors.dm.LibrariesForLibs

plugins { id("glossarist-kmp") }

val libs = the<LibrariesForLibs>()

kotlin {

    sourceSets {
        commonMain.dependencies { implementation(libs.napier) }
    }
}
