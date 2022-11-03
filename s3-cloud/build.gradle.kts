plugins {
    kotlin("jvm")
}

val coroutinesVersion: String by project
val koinVersion: String by project

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":domain"))

    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Koin core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    //AWS S3
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.328")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
//    implementation("aws.sdk.kotlin:s3:0.17.5-beta")

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}