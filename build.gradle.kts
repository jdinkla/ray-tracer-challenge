import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

group = "dinkla.net"
version = "0.1-SNAPSHOT"

val junitVersion = "5.9.0"
val cucumberVersion = "7.8.1"
val cucumberReport = "pretty"
val kotlinxCoroutinesVersion = "1.7.3"
val kotestVersion = "5.8.0"

plugins {
    kotlin("jvm") version "1.9.21"
    id("io.kotest.multiplatform") version "5.4.2"
    id("io.gitlab.arturbosch.detekt").version("1.23.4")
}

repositories {
    mavenCentral()
    maven {
        url = URI.create("https://kotlin.bintray.com/kotlinx")
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-engine:$kotestVersion")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

configurations {}

val cucumberRuntime by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

task("cucumber") {
    dependsOn("assemble", "compileTestJava")
    doLast {
        javaexec {
            mainClass = "io.cucumber.core.cli.Main"
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            args =
                listOf(
                    "--plugin",
                    cucumberReport,
                    "--glue",
                    "net.dinkla.raytracerchallenge.stepdefs",
                    "src/test/resources",
                )
            jvmArgs = listOf("-Dfile.encoding=utf-8", "-ea")
        }
    }
}

detekt {
    input = files("src/main/kotlin", "src/test/kotlin")
    config = files("detekt-config.yml")
}

task("pre_commit") {
    dependsOn("test")
    dependsOn("cucumber")
    dependsOn("detekt")
}
