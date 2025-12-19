import org.gradle.accessors.dm.LibrariesForLibs
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType


plugins { id("org.jlleitschuh.gradle.ktlint") }

val libs = the<LibrariesForLibs>()

ktlint {
    version.set("1.7.1")
    android.set(true)
    ignoreFailures.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")

    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
    }

    filter {
        exclude("**/generated/**")
        exclude("**/build/**")
    }
}

dependencies { ktlintRuleset(libs.compose.rules) }
