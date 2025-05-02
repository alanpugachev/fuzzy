
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20"
}

group = "com.alanpugachev"
version = "0.1.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    /* ktor */
    implementation("io.ktor:ktor-server-html-builder:3.1.2")
    implementation("io.ktor:ktor-server-resources:3.1.2")

    /* serialization */
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    /* kafka */
    implementation("org.apache.kafka:kafka-clients:3.6.0")

    /* log4j */
    implementation("org.slf4j:slf4j-simple:2.0.9")

    /* exposed */
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    runtimeOnly("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.61.0")
    implementation("org.jetbrains.exposed:exposed-json:0.61.0")

    /* postgresql */
    implementation("org.postgresql:postgresql:42.7.5")
}
