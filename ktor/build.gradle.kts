import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val kotlinVersion: String by project
val koinVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project
val kmongoVersion: String by project

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.1.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
	"io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

group = "ru.medals"
version = "0.0.1"

plugins {
	application
	kotlin("jvm")
	/*	id("com.bmuschko.docker-java-application")
		id("com.bmuschko.docker-remote-api")*/
	id("com.github.johnrengelman.shadow")
}

application {
	mainClass.set("io.ktor.server.netty.EngineMain")
	project.setProperty("mainClassName", mainClass.get())

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")

}

repositories {
	maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

val sshAntTask = configurations.create("sshAntTask")

dependencies {
	implementation(project(":data"))
	implementation(project(":domain"))
	implementation(project(":s3-cloud"))

//	implementation(kotlin("stdlib-common"))
	implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
	implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

	// gson
	implementation(ktor("gson", "serialization")) // io.ktor:ktor-serialization-jackson
	implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation
//    implementation(ktor("kotlinx-json", "serialization")) // io.ktor:ktor-serialization-kotlinx-json

	implementation(ktor("caching-headers"))
	implementation(ktor("call-logging"))
	implementation(ktor("auto-head-response"))
	implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
	implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
	implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
	implementation(ktor("auto-head-response"))
	implementation(ktor("websockets"))
//	implementation(ktor("partial-content")) // Для загрузки файлов

	implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
	implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
	implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

	// Koin core features
	implementation("io.insert-koin:koin-core:$koinVersion")
	implementation("io.insert-koin:koin-ktor:$koinVersion")
	implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
	testImplementation("io.insert-koin:koin-test:$koinVersion")

	// Send email service
	implementation("org.apache.commons:commons-email:1.5")

	implementation("ch.qos.logback:logback-classic:$logbackVersion")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

//	testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
//	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

	implementation(ktor("content-negotiation", prefix = "client-"))
	implementation(ktor("websockets", prefix = "client-"))

	implementation(kotlin("test-common"))
	implementation(kotlin("test-annotations-common"))
	implementation(kotlin("test-junit"))

	implementation(ktor("test-host"))

	sshAntTask("org.apache.ant:ant-jsch:1.10.12")

}

tasks.withType<ShadowJar> {
	manifest {
		attributes(
			"Main-Class" to application.mainClass.get()
		)
		archiveFileName.set("server.jar")
	}
}

task("Local copy docker") {
	dependsOn("clean", "shadowJar")
	copy {
		from("build/libs/server.jar")
		into("../dockers/main")
	}
}

ant.withGroovyBuilder {
	"taskdef"(
		"name" to "scp",
		"classname" to "org.apache.tools.ant.taskdefs.optional.ssh.Scp",
		"classpath" to configurations["sshAntTask"].asPath
	)
	"taskdef"(
		"name" to "ssh",
		"classname" to "org.apache.tools.ant.taskdefs.optional.ssh.SSHExec",
		"classpath" to configurations["sshAntTask"].asPath
	)
}

task("deploy") {
	dependsOn(/*"clean", */"shadowJar")
	ant.withGroovyBuilder {
		doLast {
			val knownHosts = File.createTempFile("knownhosts", "txt")
			val user = "nifont"
			val host = "192.168.1.106"
			val key = file("d:/deploy/serverkey")
			val jarFileName = "server.jar"
			try {
				"scp"(
					"file" to file("build/libs/$jarFileName"),
					"todir" to "$user@$host:~/medals",
					"keyfile" to key,
					"trust" to true,
					"knownhosts" to knownHosts
				)
			} finally {
				knownHosts.delete()
			}
		}
	}
}

task("docker") {
	dependsOn("clean", "shadowJar")
	ant.withGroovyBuilder {
		doLast {
			val knownHosts = File.createTempFile("knownhosts", "txt")
			val user = "nifont"
			val host = "192.168.1.106"
			val key = file("d:/deploy/serverkey")
			val jarFileName = "server.jar"
			try {
				"scp"(
					"file" to file("build/libs/$jarFileName"),
					"todir" to "$user@$host:~/medals",
					"keyfile" to key,
					"trust" to true,
					"knownhosts" to knownHosts
				)
				"ssh"(
					"host" to host,
					"username" to user,
					"keyfile" to key,
					"trust" to true,
					"knownhosts" to knownHosts,
					"command" to "cd ~/medals; docker compose build; docker compose up -d"
				)
			} finally {
				knownHosts.delete()
			}
		}
	}
}

val remoteUrl = "nmedalist.ru"
val patchKey = "d:/deploy/reg/medalist/remotekey"

task("remote-docker") {
	dependsOn("clean", "shadowJar")
	ant.withGroovyBuilder {
		doLast {
			val knownHosts = File.createTempFile("knownhosts", "txt")
			val user = "nifont"
			val host = remoteUrl
			val key = file(patchKey)
			val jarFileName = "server.jar"
			try {
				"scp"(
					"file" to file("build/libs/$jarFileName"),
					"todir" to "$user@$host:~/medals",
					"keyfile" to key,
					"trust" to true,
					"knownhosts" to knownHosts
				)
				"ssh"(
					"host" to host,
					"username" to user,
					"keyfile" to key,
					"trust" to true,
					"knownhosts" to knownHosts,
					"command" to "cd ~; docker compose build; docker compose up -d"
				)
			} finally {
				knownHosts.delete()
			}
		}
	}
}

task("remote-deploy") {
	dependsOn("clean", "shadowJar")
	ant.withGroovyBuilder {
		doLast {
			val knownHosts = File.createTempFile("knownhosts", "txt")
			val user = "nifont"
			val host = remoteUrl
			val key = file(patchKey)
			val jarFileName = "server.jar"
			try {
				"scp"(
					"file" to file("build/libs/$jarFileName"),
					"todir" to "$user@$host:~/medals",
					"keyfile" to key,
					"trust" to true,
					"knownhosts" to knownHosts
				)
			} finally {
				knownHosts.delete()
			}
		}
	}
}