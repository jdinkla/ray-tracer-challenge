package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.math.Vector
import net.dinkla.raytracerchallenge.math.vector
import net.dinkla.raytracerchallenge.objects.Cube
import org.junit.jupiter.api.Assertions.assertEquals

class CubesStepDefinitions {

    private lateinit var cube: Cube
    private lateinit var normal: Vector

    @Given("c ← cube\\()")
    fun c_cube() {
        cube = Cube()
    }

    @When("xs ← local_intersect\\(c, r)")
    fun xs_local_intersect_c_r() {
        xs = cube.intersectInObjectSpace(r)
    }

    @When("normal ← local_normal_at\\(c, p)")
    fun normal_local_normal_at_c_p() {
        normal = cube.normalInObjectSpace(p)
    }

    @Then("normal = vector\\({int}, {int}, {int})")
    fun normal_vector(int1: Int?, int2: Int?, int3: Int?) {
        assertEquals(vector(int1!!, int2!!, int3!!), normal)
    }
}
