import java.net.URI

plugins {
    id("java")
    `java-test-fixtures`
    alias(libs.plugins.axion.release)
    id("maven-publish")
}

group = "at.elmecker"
version = scmVersion.version

repositories {
    mavenCentral()
}

dependencies {
    testRuntimeOnly(libs.junit.platform.launcher)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj.core)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI.create("https://maven.pkg.github.com/AElmecker/midi-remap")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}