# Todo

- √2/2 was bugged in JVM cucumber, replaced it with 0.7071



Beispiel auf S. 97

Der umständlich zu lesende Text entspricht

```kotlin
fun colorAt(ray: Ray): Color {
    val xs = intersect(ray)
    val hit = xs.hit()
    if (hit == null) {
        return Color.BLACK
    } else {
        val comps = Computations.prepare(hit, ray)
        return shadeHit(comps)
    }
}
```


## Todo

- fluent api, see p55

val b = 2 * (ray.direction dot sphereToRay) needs parenthesis

## Questions

- Why is unaryMinus() negating w, so (- tuple(1, 2, 3, 4)).z == -4
- when do we clamp the colors?
- matrix equality with epsilon




### Empty params

Scenario: The hit, when some intersections have negative t
Given s ← sphere()



    @Given("s ← sphere\\()")
    fun s_sphere() {
        s = Sphere()
    }

Step undefined
You can implement missing steps with the snippets below:
@Given("s ← sphere\\()")
public void s_sphere() {
// Write code here that turns the phrase above into concrete actions
throw new io.cucumber.java.PendingException();
}

# the following scenario does not work with cucumber JVM
#
# Scenario: Reflecting a vector off a slanted surface
#  Given v ← vector(0, -1, 0)
#    And n ← vector(√2/2, √2/2, 0.0)
#  When r ← reflect(v, n)
#  Then r = vector(1, 0, 0)
#
#Exception in thread "main" io.cucumber.core.exception.CompositeCucumberException: There were 30 exceptions:
#java.util.regex.PatternSyntaxException(Unclosed group near index 171
#^n �? vector(√((?:-?\d+)|(?:\d+))/((?:-?\d+)|(?:\d+)), √((?:-?\d+)|(?:\d+))/((?:-?\d+)|(?:\d+)), ((?=.*\d.*)[-+]?(?:\d+(?:[,]?\d+)*)*(?:[.](?=\d.*))?\d*(?:\d+[E]-?\d+)?)\)$)
#
# So i hardcoded sqrt(2)/2
#Scenario: Reflecting a vector off a slanted surface
#  Given v ← vector(0, -1, 0)
#    And n ← vector(0.70710678118, 0.70710678118, 0.0)
#  When r ← reflect(v, n)
#  Then r = vector(1, 0, 0)




Other pages
https://github.com/twistdroach/raytracer-c

