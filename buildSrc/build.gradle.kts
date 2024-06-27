repositories {
    mavenCentral()
}
plugins {
    kotlin("plugin.serialization") version "1.9.23"
    `kotlin-dsl`
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}