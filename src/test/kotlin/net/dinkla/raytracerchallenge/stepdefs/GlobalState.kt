package net.dinkla.raytracerchallenge.stepdefs

import net.dinkla.raytracerchallenge.Intersections
import net.dinkla.raytracerchallenge.Material
import net.dinkla.raytracerchallenge.PointLight
import net.dinkla.raytracerchallenge.Ray
import net.dinkla.raytracerchallenge.math.*
import net.dinkla.raytracerchallenge.objects.Sphere

lateinit var c1: Color
lateinit var c2: Color
lateinit var c3: Color
lateinit var c4: Color

lateinit var a_tuple: Tuple

lateinit var p: Point
lateinit var v: Vector

lateinit var r: Ray

lateinit var s: Sphere

lateinit var xs: Intersections

lateinit var transform : Matrix

lateinit var m: Material

lateinit var light: PointLight
lateinit var intensity: Color
lateinit var position: Point

