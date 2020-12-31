package net.dinkla.raytracerchallenge.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.point
import kotlin.test.assertEquals

class LightsStepDefinitions {

    lateinit var intensity: Color
    lateinit var position: Point
    lateinit var light: PointLight

    @Given("intensity ← color\\({int}, {int}, {int})")
    fun intensity_color(int1: Int?, int2: Int?, int3: Int?) {
        intensity = Color(int1!!, int2!!, int3!!)
    }

    @Given("position ← point\\({int}, {int}, {int})")
    fun position_point(int1: Int?, int2: Int?, int3: Int?) {
        position = point(int1!!, int2!!, int3!!)
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
