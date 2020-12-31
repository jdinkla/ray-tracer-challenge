package net.dinkla.raytracerchallenge.objects

import net.dinkla.raytracerchallenge.Ray

interface GeometricObject {

    fun intersect(ray: Ray): List<Double>

}