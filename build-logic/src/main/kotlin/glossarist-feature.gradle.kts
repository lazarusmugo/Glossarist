import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("glossarist-kmp-library")
    id("glossarist-code-format")
    id("glossarist-logger")
    id("glossarist-cmp")
}

val libs = the<LibrariesForLibs>()

kotlin {

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
        }
    }
}
