package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Transformation.scaling
import net.dinkla.raytracerchallenge.math.point
import net.dinkla.raytracerchallenge.objects.GeometricObject
import net.dinkla.raytracerchallenge.objects.Sphere

class World {

    val objects: MutableList<GeometricObject> = mutableListOf()
    val lights: MutableList<PointLight> = mutableListOf()


    companion object {
        fun defaultWorld(): World {
            val w = World()
            val position = point(-10, 10, -10)
            val intensity = Color.WHITE
            val light = PointLight(position, intensity)
            w.lights.add(light)

            val s1 = Sphere()
            s1.material.color = Color(0.8, 1.0, 0.6)
            s1.material.diffuse = 0.7
            s1.material.specular = 0.2
            w.objects.add(s1)

            val s2 = Sphere()
            s2.transform = scaling(0.5, 0.5, 0.5)
            w.objects.add(s2)

            return w
        }
    }
}