import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dinkla.net"
version = "0.1-SNAPSHOT"

val junitVersion = "5.6.2"
val cucumberVersion = "6.9.1"
val cucumberReport = "pretty"
// val cucumberReport = "html:build/cucumber-report.html"

plugins {
    kotlin("jvm") version "1.4.21"
    id("io.gitlab.arturbosch.detekt").version("1.15.0")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
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
