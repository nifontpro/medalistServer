plugins {
    kotlin("jvm")
}

val koinVersion: String by project
val coroutinesVersion: String by project

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":cor"))
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Koin core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    implementation(group = "org.mindrot", name = "jbcrypt", version = "0.4")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}