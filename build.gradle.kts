plugins {
//    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
    kotlin("jvm") version "1.9.23"
    application
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "org.netease"
version = "1.0.2"

repositories {
//    mavenCentral()
    maven(url="https://maven.aliyun.com/repository/public/")
}

dependencies {
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("org.openjdk.nashorn:nashorn-core:15.4")
    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("org.riversun:okhttp3-cookie-helper:1.0.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}