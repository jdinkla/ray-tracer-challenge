# Status

Implementation status and open notes for this Kotlin port of *The Ray Tracer Challenge*.
(Formerly `TODO.md`, kept as a living status doc.)

## Implementation status

The book is fully implemented — chapters 1–16. Every `src/test/resources/*.feature` is enabled and
green (313 Cucumber scenarios plus the kotest specs; detekt clean).

- **Primitives:** sphere, plane, cube, cylinder, cone, triangle, smooth triangle.
- **Containers:** group (nested transforms) and CSG (union / intersection / difference).
- **Shading:** Phong, shadows, patterns (stripe / gradient / ring / checkers), reflection, and
  refraction blended with the Schlick (Fresnel) approximation.
- **Meshes:** load from Wavefront OBJ (`parser/ObjParser`).
- Each chapter has a runnable example under `examples/`, e.g.
  `./gradlew render -Pchapter=Chapter16Csg`.

## Known limitations & deliberate decisions

- **Cucumber-JVM can't parse `√` / `π` in step regexes.** Those literals are hardcoded as decimals in
  the feature files (e.g. `√2/2` → `0.70710678118`, `π/2` → `1.5707963267948966`). The original
  failure is recorded under "Cucumber-JVM gotchas" below.
- **`Approx.EPSILON` is `1e-5`, and `ApproxTest` deliberately pins it** (it asserts a `1.12e-5`
  difference is *not* small). A few book reference values are only `1e-4`-precise (rounded to 4–5
  decimals), so the scenarios asserting them are unsatisfiable at `1e-5` regardless of the
  implementation. Those stay commented (with a note) in their feature files and are instead verified
  at `1e-4` by `RefractedColorSpec` / `GroupSpec`:
  - `world.feature` — "The refracted color with a refracted ray"
  - `shapes.feature` — "Converting a normal from object to world space", "Finding the normal on a child object"

  Don't loosen `EPSILON` to "fix" these — it breaks `ApproxTest`.
- **No spatial acceleration.** Groups, CSG, and meshes are brute-force: every ray tests every
  primitive. Fine at current example sizes (renders are 1–3 s), but it's the obvious next step.

## Possible next steps

- Bounding boxes / bounding-volume hierarchies (the book's "Bounding boxes and hierarchies" bonus
  chapter) so groups and OBJ meshes scale. There is no feature file for it in the repo yet.
- A fluent construction API for shapes/materials (book p. 55).

## Open questions & notes

- **`unaryMinus()` negates `w` too** — `(-tuple(1, 2, 3, 4)).w == -4`. Intentional? It only matters
  for tuples that are neither a pure point nor a pure vector.
- **When do we clamp colours?** Only at output, in `Color.toRGB()` / `Color.toInt()`.
- **Matrix equality with epsilon** — *resolved:* `Matrix`, `Tuple`, and `Color` all compare
  element-wise via `Approx.isDifferenceSmall` (`1e-5`).
- **p. 130 — should `lighting` distinguish "has a pattern" from "no pattern"?** The material knows
  better. *Resolved:* the pattern check lives in `Material.color(shape, point)`; `lighting` only
  branches on whether a shape is supplied, not on patterns.
- **p. 97 — the book's `colorAt` reads awkwardly; the clean Kotlin is the `if (hit == null) … else …`
  form.** *Done:* that is what `World.colorAt` uses (now also threading `remaining` for recursion).

## Cucumber-JVM gotchas (for reference)

Empty-paren steps such as `s ← sphere()` need the `(` escaped in the annotation:

```kotlin
@Given("s ← sphere\\()")
fun s_sphere() { s = Sphere() }
```

The `√` parse failure that prompted the decimals workaround:

```
# Scenario: Reflecting a vector off a slanted surface
#   And n ← vector(√2/2, √2/2, 0.0)
# -> java.util.regex.PatternSyntaxException: Unclosed group near index 171
# So it is hardcoded as vector(0.70710678118, 0.70710678118, 0.0).
```

Shapes referenced by name across step classes go through the `namedShapes` registry in
`stepdefs/GlobalState.kt` (cleared per scenario); the generic `xs ← intersections(t:name, …)` parser
in `RefractionStepDefinitions` resolves those names.

## References

- The book: <http://www.raytracerchallenge.com/>
- Another port (C): <https://github.com/twistdroach/raytracer-c>
