package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Color.Companion.BLACK
import net.dinkla.raytracerchallenge.math.Point
import net.dinkla.raytracerchallenge.math.Vector
import kotlin.math.pow

fun lighting(light: PointLight, comps: Computations, inShadow: Boolean = false): Color {
    val material = comps.`object`.material
    val point = comps.overPoint
    val normalV = comps.normalV
    val eyeV = comps.eyeV
    return lighting(material, light, point, eyeV, normalV, inShadow)
}

fun lighting(material: Material, light: PointLight, point: Point, eyeV: Vector, normalV: Vector, inShadow: Boolean = false): Color {
    val color = material.color(point)
    val effectiveColor = color * light.intensity
    val ambient = effectiveColor * material.ambient
    if (inShadow) {
        return ambient
    }
    var diffuse: Color = BLACK
    var specular: Color = BLACK
    val lightV = (light.position - point).normalize()
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