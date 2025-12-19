import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.vanniktech.maven.publish")
}

val versionProps = Properties().apply { load(FileInputStream(rootProject.file("cz.toml"))) }
val libVersion = versionProps.getProperty("version").replace("\"", "")

project.version = libVersion
project.group = "io.github.lazarusmugo"

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates("io.github.lazarusmugo", "glossarist", libVersion)

    pom {
        name.set("Glossarist")
        description.set("A simple, type-safe internationalization library for Kotlin Multiplatform projects.")
        inceptionYear.set("2025")
        url.set("https://github.com/lazarusmugo/Glossarist")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("lazarusmugo")
                name.set("Lazarus Mugo")
                url.set("https://github.com/lazarusmugo/")
            }
        }
        scm {
            url.set("https://github.com/lazarusmugo/Glossarist/")
            connection.set("scm:git:git://github.com/lazarusmugo/Glossarist.git")
            developerConnection.set("scm:git:ssh://git@github.com/lazarusmugo/Glossarist.git")
        }
    }
}
