# Todo

- √2/2 was bugged in JVM cucumber, replaced it with 0.7071


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
