import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "dinkla.net"
version = "0.1-SNAPSHOT"

val cucumberVersion = "7.22.1"
val cucumberReport = "pretty"
val kotlinxCoroutinesVersion = "1.11.0"
val kotestVersion = "5.9.1"

plugins {
    kotlin("jvm") version "2.4.0"
    id("dev.detekt").version("2.0.0-alpha.4")
}

repositories {
    mavenCentral()
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

kotlin {
    jvmToolchain(25)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

val cucumberRuntime by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

tasks.register<JavaExec>("cucumber") {
    dependsOn("assemble", "compileTestJava")
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

tasks.register<JavaExec>("render") {
    group = "application"
    description = "Render an example, e.g. ./gradlew render -Pchapter=Chapter7 (default: All)"
    dependsOn("classes")
    classpath = sourceSets.main.get().runtimeClasspath
    val chapter = (project.findProperty("chapter") as String?) ?: "All"
    mainClass = "net.dinkla.raytracerchallenge.examples.${chapter}Kt"
}

detekt {
    source.setFrom("src/main/kotlin", "src/test/kotlin")
    config.setFrom("detekt-config.yml")
}

tasks.register("pre_commit") {
    dependsOn("test")
    dependsOn("cucumber")
    dependsOn("detekt")
}
