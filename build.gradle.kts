plugins {
    id("java")
    `java-test-fixtures`
    alias(libs.plugins.axion.release)
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