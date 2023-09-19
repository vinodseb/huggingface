plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-config-yaml:2.3.4")
    implementation("io.ktor:ktor-server-core:2.3.4")
    implementation("io.ktor:ktor-server-netty:2.3.4")
    implementation("ch.qos.logback:logback-classic:1.4.7")

    implementation("ai.djl:bom:0.22.1")
    implementation("ai.djl:api:0.22.1")
    implementation("ai.djl.pytorch:pytorch-engine:0.22.1")
    implementation("ai.djl.pytorch:pytorch-model-zoo:0.22.1")
}

application {
    mainClass.set("com.vinodseb.mltest.ApplicationKt")
}
