package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.point
import org.junit.jupiter.api.Assertions.assertEquals

class LightsStepDefinitions {

    @Given("intensity ← color\\({int}, {int}, {int})")
    fun intensity_color(int1: Int?, int2: Int?, int3: Int?) {
        intensity = Color(int1!!, int2!!, int3!!)
    }

    @Given("position ← point\\({double}, {double}, {double})")
    fun position_point(double1: Double?, double2: Double?, double3: Double?) {
        position = point(double1!!, double2!!, double3!!)
    }

    @When("light ← point_light\\(position, intensity)")
    fun light_point_light_position_intensity() {
        light = PointLight(position, intensity)
    }

    @Then("light.position = position")
    fun light_position_position() {
       assertEquals(position, light.position)
    }

    @Then("light.intensity = intensity")
    fun light_intensity_intensity() {
        assertEquals(intensity, light.intensity)
    }

}
