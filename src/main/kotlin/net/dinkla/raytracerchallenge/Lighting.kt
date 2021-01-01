package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Color.Companion.BLACK
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import kotlin.math.pow

fun lighting(light: PointLight, comps: Computations): Color {
    val material = comps.`object`.material
    val point = comps.point
    val normalV = comps.normalV
    val eyeV = comps.eyeV
    return lighting(material, light, point, eyeV, normalV)
}

fun lighting(material: Material, light: PointLight, point: Point, eyeV: Vector, normalV: Vector): Color {
    var diffuse: Color = BLACK
    var specular: Color = BLACK
    val effectiveColor = material.color * light.intensity
    val lightV = (light.position - point).normalize()
    val ambient = effectiveColor * material.ambient
    val lightDotNormal = lightV dot normalV
    if (lightDotNormal >= 0.0) {
        diffuse = effectiveColor * material.diffuse * lightDotNormal
        val reflectV = (-lightV).reflect(normalV)
        val reflectDotEye = reflectV dot eyeV
        if (reflectDotEye >= 0.0) {
            val factor = reflectDotEye.pow(material.shininess)
            specular = light.intensity * material.specular * factor
        }
    }
    return ambient + diffuse + specular
}