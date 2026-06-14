# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A Kotlin implementation of the book *The Ray Tracer Challenge* (Jamis Buck). Code lives under
`net.dinkla.raytracerchallenge`. The book drives both the structure (one concept per chapter) and
the tests (the book's Gherkin specs are checked in as Cucumber `.feature` files).

## Commands

Requires a JDK 25 toolchain for the Gradle wrapper. In a non-interactive shell where SDKMAN's init
hasn't run, `JAVA_HOME` may be unset — prefix Gradle calls with
`JAVA_HOME="$HOME/.sdkman/candidates/java/current"` (or whatever JDK 25 you have).

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

Two recurring gotchas when enabling book scenarios:
- Cucumber-JVM can't parse `√`/`π` in step regexes, so those literals are hardcoded as decimals
  (e.g. `√2/2` → `0.70710678118`). See `TODO.md`.
- `Approx.EPSILON` is `1e-5` and `ApproxTest` deliberately pins that. A few book reference values are
  only `1e-4`-precise, so the scenarios that assert them are unsatisfiable at `1e-5` and stay
  commented (with a note) in the feature file, backed instead by a `*Spec` that asserts at `1e-4`
  (`RefractedColorSpec`, `GroupSpec`). Don't "fix" these by loosening `EPSILON` — it breaks `ApproxTest`.

Step definitions refer to shapes by name across step classes through the `namedShapes` registry in
`GlobalState.kt` (cleared per scenario). The generic `xs ← intersections(t:name, …)` parser
(`RefractionStepDefinitions`) resolves those names — reuse it rather than adding bespoke intersection
steps.

The whole book is implemented: every `src/test/resources/*.feature` is enabled and green.

## Architecture

The core type is `math/Tuple` — a `(x, y, z, w)` data class with `typealias Point = Tuple` and
`typealias Vector = Tuple`. `w == 1.0` is a point, `w == 0.0` a vector; operators carry `assert`s
enforcing that distinction. Float comparisons go through `math/Approx` (epsilon), never `==`.

Two parallel "shape-space" abstractions, both using the same trick — transform the input into local
space via the inverse matrix, delegate to a subclass hook, transform the result back:

- **`objects/Shape`** (abstract): holds a `transform`, `Material`, and a `parent` (for nesting).
  Subclasses implement `intersectInObjectSpace` / `normalInObjectSpace`; the base `intersect` /
  `normal` handle world↔object-space conversion. For nested shapes that conversion recurses through
  parents via `worldToObject` / `normalToWorld`. Primitives: `Sphere`, `Plane`, `Cube`, `Cylinder`,
  `Cone`, `Triangle`, `SmoothTriangle`. Containers: `Group` (combines children) and `Csg`
  (union/intersection/difference, filtering child intersections by `intersectionAllowed` using
  `Shape.includes`). `Cylinder`/`Cone` carry `minimum`/`maximum`/`closed`.
- **`patterns/Pattern`** (abstract): subclasses implement `atInPatternSpace`; `at(shape, point)`
  converts world → object → pattern space. Concrete: `Stripe`, `Gradient`, `Ring`, `Checkers`.

`Intersection` carries optional barycentric `u`/`v` (smooth triangles). `normal(point, hit)` and
`normalInObjectSpace(point, hit)` take the hit so `SmoothTriangle` can interpolate; the two-arg form
defaults to the one-arg version, so other shapes are unaffected. `parser/ObjParser` loads Wavefront
OBJ meshes (1-indexed vertices/normals, fan-triangulation, named groups → `Group`s).

`World` ties it together: a list of `Shape`s plus a `PointLight`. `colorAt` → `intersect` →
`Computations.prepare(hit, ray, xs)` → `shadeHit`: Phong (`Lighting` + shadows) plus recursive
`reflectedColor` and `refractedColor`, combined by the Fresnel `schlick` term when a surface is both
reflective and transparent. Recursion is bounded by `MAX_RECURSION = 5`. `World.render` picks
`renderInParallel` (kotlinx-coroutines, `Dispatchers.Default`, 4×4 pixel tiles) when both camera
dimensions are divisible by 4, else `renderSequential`. `Camera` + `ViewTransformation` produce the
rays; `ui/PNG` and `ui/PPM` write the resulting `Canvas`.

Note `Computations.object` is accessed as `` comps.`object` `` — `object` is a Kotlin keyword.
Each book chapter/feature has a runnable example under `examples/` (e.g. `Chapter16Csg`); render with
`./gradlew render -Pchapter=Chapter16Csg`. Groups/CSG/meshes are brute-force (no bounding-box
acceleration yet), so keep example resolutions modest.

## Toolchain notes

Kotlin 2.4.0, JVM target/toolchain 25. detekt is `2.0.0-alpha.4` and its config is `detekt-config.yml`
(stricter than defaults — documentation, complexity, and exception rules are active). detekt pins the
rest of the stack on upgrades; prefer keeping deps on stable releases.
