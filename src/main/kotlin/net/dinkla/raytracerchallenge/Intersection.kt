package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.objects.Shape

open class Intersection(
    val t: Double,
    val `object`: Shape,
    val u: Double = Double.NaN,
    val v: Double = Double.NaN,
)
