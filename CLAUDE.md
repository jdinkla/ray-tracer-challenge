# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A Kotlin implementation of the book *The Ray Tracer Challenge* (Jamis Buck). Code lives under
`net.dinkla.raytracerchallenge`. The book drives both the structure (one concept per chapter) and
the tests (the book's Gherkin specs are checked in as Cucumber `.feature` files).

## Commands

Requires a JDK 25 toolchain on `PATH` (e.g. via SDKMAN) for the Gradle wrapper.

| Task | Gradle | just |
| --- | --- | --- |
| Unit tests (kotest) | `./gradlew test` | `just test` |
| Single unit test | `./gradlew test --tests '*CameraSpec*'` | `just test --tests '*CameraSpec*'` |
| Acceptance tests (Cucumber) | `./gradlew cucumber` | `just cucumber` |
| Static analysis | `./gradlew detekt` | `just lint` |
| Full quality gate | `./gradlew pre_commit` | `just check` |
| Render an example to PNG | `./gradlew render -Pchapter=Chapter7` | `just render Chapter7` |
| Build everything | `./gradlew build` | `just build` |

- `pre_commit` (Gradle) = `test` + `cucumber` + `detekt`. `just check` = `lint` + `test` + `cucumber`.
- `render` runs `examples.<chapter>Kt.main()`; `chapter` defaults to `All`. Output PNGs land in the
  gitignored `/generated/` directory.
- `cucumber` is a custom `JavaExec` task (not part of `test`): glue is
  `net.dinkla.raytracerchallenge.stepdefs`, features are `src/test/resources/*.feature`. It runs
  with `-ea` (assertions on) — `Tuple` and others rely on `assert(...)` invariants.

## Two test layers

1. **Cucumber acceptance tests** (`src/test/resources/*.feature` + `stepdefs/*StepDefinitions.kt`)
   transcribe the book's specs. Step definitions share one big bag of mutable top-level
   `lateinit var`s in `stepdefs/GlobalState.kt` (`s`, `xs`, `w`, `comps`, …) — a scenario sets them
   in `@Given`/`@When` and asserts in `@Then`. When adding steps, reuse these variables rather than
   introducing locals.
2. **kotest specs** (`src/test/kotlin/.../*Spec.kt`, `*Test.kt`) run on the JUnit 5 platform via
   `./gradlew test`. Cucumber does **not** run under `test`.

Not every book scenario survives Cucumber-JVM's regex parsing — see `TODO.md` for cases where `√2/2`
literals had to be hardcoded as decimals (e.g. `0.7071`).

## Architecture

The core type is `math/Tuple` — a `(x, y, z, w)` data class with `typealias Point = Tuple` and
`typealias Vector = Tuple`. `w == 1.0` is a point, `w == 0.0` a vector; operators carry `assert`s
enforcing that distinction. Float comparisons go through `math/Approx` (epsilon), never `==`.

Two parallel "shape-space" abstractions, both using the same trick — transform the input into local
space via the inverse matrix, delegate to a subclass hook, transform the result back:

- **`objects/Shape`** (abstract): holds a `transform` and `Material`. Subclasses implement
  `intersectInObjectSpace` / `normalInObjectSpace`; the base `intersect` / `normal` handle the
  world↔object-space conversion (normals use the inverse-transpose). Concrete shapes: `Sphere`,
  `Plane`.
- **`patterns/Pattern`** (abstract): subclasses implement `atInPatternSpace`; `at(shape, point)`
  converts world → object → pattern space. Concrete: `Stripe`, `Gradient`, `Ring`, `Checkers`.

`World` ties it together: a list of `Shape`s plus a `PointLight`. `colorAt` → `intersect` →
`Computations.prepare(hit, ray)` → `shadeHit` (Phong via `Lighting` + shadows + recursive
`reflectedColor`). Reflection recursion is bounded by `MAX_RECURSION = 5`. `World.render` picks
`renderInParallel` (kotlinx-coroutines, `Dispatchers.Default`, 4×4 pixel tiles) when both camera
dimensions are divisible by 4, else `renderSequential`. `Camera` + `ViewTransformation` produce the
rays; `ui/PNG` and `ui/PPM` write the resulting `Canvas`.

Note `Computations.object` is accessed as `` comps.`object` `` — `object` is a Kotlin keyword.

## Toolchain notes

Kotlin 2.4.0, JVM target/toolchain 25. detekt is `2.0.0-alpha.4` and its config is `detekt-config.yml`
(stricter than defaults — documentation, complexity, and exception rules are active). detekt pins the
rest of the stack on upgrades; prefer keeping deps on stable releases.
