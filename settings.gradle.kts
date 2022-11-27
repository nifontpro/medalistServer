rootProject.name = "ru.medals.server"

pluginManagement {
	plugins {
		val kotlinVersion: String by settings
		val shadowVersion: String by settings

		kotlin("jvm") version kotlinVersion apply false
		id("com.github.johnrengelman.shadow") version shadowVersion apply false
	}
}

include("cor")
include("ktor")
include("domain")
include("data")
include("s3-cloud")
include("logging")
