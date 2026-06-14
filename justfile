# justfile for ray-tracer-challenge-kotlin — common workflow shortcuts
# Run `just --list` to see all targets.
# Requires a JDK 25 toolchain on PATH (e.g. via SDKMAN) for the Gradle wrapper.
#
#   $ just check     # full quality gate: detekt + unit tests + cucumber

# --- Dev ---

[group('dev')]
[doc("Show available commands")]
default:
    @just --list --unsorted

[group('dev')]
[doc("Run static analysis (detekt)")]
lint:
    ./gradlew detekt

[group('dev')]
[doc("Run the full quality gate: lint + unit tests + cucumber")]
check: lint test cucumber

# --- Build ---

[group('build')]
[doc("Compile main and test sources")]
compile:
    ./gradlew classes testClasses

[group('build')]
[doc("Assemble and run all checks (build + detekt + tests)")]
build:
    ./gradlew build

[group('build')]
[doc("Remove build outputs")]
clean:
    ./gradlew clean

[group('build')]
[doc("Render an example to PNG, e.g. `just render Chapter7` (default: All)")]
render CHAPTER="All":
    ./gradlew render -Pchapter={{CHAPTER}}

# --- Test ---

[group('test')]
[doc("Run unit tests (kotest); pass extra Gradle args, e.g. --tests '*CameraSpec*'")]
test *ARGS:
    ./gradlew test {{ARGS}}

[group('test')]
[doc("Run the Cucumber acceptance tests")]
cucumber:
    ./gradlew cucumber
