package net.dinkla.raytracerchallenge.stepdefs

import net.dinkla.raytracerchallenge.*
import net.dinkla.raytracerchallenge.math.*
import net.dinkla.raytracerchallenge.objects.Shape
import net.dinkla.raytracerchallenge.objects.Sphere

lateinit var c1: Color
lateinit var c2: Color
lateinit var c3: Color
lateinit var c4: Color

lateinit var a_tuple: Tuple

lateinit var p: Point
lateinit var v: Vector

lateinit var r: Ray

lateinit var s: Shape

lateinit var xs: Intersections

lateinit var transform : Matrix

lateinit var m: Material

lateinit var light: PointLight
lateinit var intensity: Color
lateinit var position: Point

var i: Intersection? = null

lateinit var comps: Computations

lateinit var shape: Shape

lateinit var w: World

lateinit var from: Point
lateinit var to: Point
lateinit var up: Vector

lateinit var n: Vector
