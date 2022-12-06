plugins {
	kotlin("jvm")
}

val coroutinesVersion: String by project
val kmongoVersion: String by project
val commonsCodecVersion: String by project
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

	// KMongo
	implementation("org.litote.kmongo:kmongo:$kmongoVersion")
	implementation("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")
	implementation("commons-codec:commons-codec:$commonsCodecVersion")

	implementation(kotlin("test-common"))
	implementation(kotlin("test-annotations-common"))
	testImplementation(kotlin("test-junit"))
	api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
}