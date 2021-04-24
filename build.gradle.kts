import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

group = "dinkla.net"
version = "0.1-SNAPSHOT"

val junitVersion = "5.7.1"
val cucumberVersion = "6.10.3"
val cucumberReport = "pretty"
val kotlinxCoroutinesVersion = "1.4.2"

plugins {
    kotlin("jvm") version "1.4.32"
    id("io.gitlab.arturbosch.detekt").version("1.16.0")
}

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = URI.create("https://kotlin.bintray.com/kotlinx")
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinxCoroutinesVersion}")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

configurations {}

val cucumberRuntime by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

task("cucumber") {
    dependsOn("assemble", "compileTestJava")
    doLast {
        javaexec {
            main = "io.cucumber.core.cli.Main"
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            args = listOf("--plugin", cucumberReport, "--glue", "net.dinkla.raytracerchallenge.stepdefs", "src/test/resources")
            jvmArgs = listOf( "-Dfile.encoding=utf-8", "-ea")
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
