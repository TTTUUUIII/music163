plugins {
    id("java")
    kotlin("jvm") version "1.9.23"
}

group = "org.netease"
version = "1.0-SNAPSHOT"

repositories {
//    mavenCentral()
    maven(url="https://maven.aliyun.com/repository/public/")
}

dependencies {
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("org.riversun:okhttp3-cookie-helper:1.0.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
//kotlin {
//    jvmToolchain("1.8")
//}