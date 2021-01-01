package net.dinkla.raytracerchallenge

import net.dinkla.raytracerchallenge.math.Color
import net.dinkla.raytracerchallenge.math.Point

data class PointLight(val position: Point = Point.ORIGIN, val intensity: Color = Color.WHITE)