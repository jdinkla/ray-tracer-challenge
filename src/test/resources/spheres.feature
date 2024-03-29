Feature: Spheres

Scenario: A ray intersects a sphere at two points and returns the objects
  Given origin ← point(0, 0, -5)
    And direction ← vector(0, 0, 1)
    And r ← ray(origin, direction)
    And s ← sphere
  When xs ← intersect(s, r)
  Then xs.count = 2
    And xs[0].t = 4.0
    And xs[1].t = 6.0
    And xs[0].object = s
    And xs[1].object = s

Scenario: A ray intersects a sphere at a tangent
  Given origin ← point(0, 1, -5)
    And direction ← vector(0, 0, 1)
    And r ← ray(origin, direction)
    And s ← sphere
  When xs ← intersect(s, r)
  Then xs.count = 2
    And xs[0].t = 5.0
    And xs[1].t = 5.0

Scenario: A ray misses a sphere
  Given origin ← point(0, 2, -5)
    And direction ← vector(0, 0, 1)
    And r ← ray(origin, direction)
    And s ← sphere
  When xs ← intersect(s, r)
  Then xs.count = 0

Scenario: A ray originates inside a sphere
  Given origin ← point(0, 0, 0)
    And direction ← vector(0, 0, 1)
    And r ← ray(origin, direction)
    And s ← sphere
  When xs ← intersect(s, r)
  Then xs.count = 2
    And xs[0].t = -1.0
    And xs[1].t = 1.0

Scenario: A sphere is behind a ray
  Given origin ← point(0, 0, 5)
   And direction ← vector(0, 0, 1)
   And r ← ray(origin, direction)
    And s ← sphere
  When xs ← intersect(s, r)
  Then xs.count = 2
    And xs[0].t = -6.0
    And xs[1].t = -4.0

#Scenario: Intersect sets the object on the intersection
#  Given origin ← point(0, 0, -5)
#    And direction ← vector(0, 0, 1)
#    And r ← ray(origin, direction)
#    And s ← sphere
#  When xs ← intersect(s, r)
#  Then xs.count = 2
#    And xs[0].object = s
#    And xs[1].object = s

Scenario: A sphere's default transformation
  Given s ← sphere
  Then s.transform = identity_matrix

Scenario: Changing a sphere's transformation
  Given s ← sphere
    And t ← translation(2, 3, 4)
  When setTransform(s, t)
  Then s.transform = t

Scenario: Intersecting a scaled sphere with a ray
  Given origin ← point(0, 0, -5)
    And direction ← vector(0, 0, 1)
    And r ← ray(origin, direction)
    And t ← scaling(2, 2, 2)
  When setTransform(s, t)
    And xs ← intersect(s, r)
  Then xs.count = 2
    And xs[0].t = 3
    And xs[1].t = 7

Scenario: Intersecting a translated sphere with a ray
  Given origin ← point(0, 0, -5)
    And direction ← vector(0, 0, 1)
    And r ← ray(origin, direction)
    And t ← translation(5, 0, 0)
  When setTransform(s, t)
    And xs ← intersect(s, r)
  Then xs.count = 0

Scenario: The normal on a sphere at a point on the x axis
  Given s ← sphere
  And p ← point(1, 0, 0)
  When n ← normalAt(s, p)
  Then n = vector(1, 0, 0)

Scenario: The normal on a sphere at a point on the y axis
  Given s ← sphere
  And p ← point(0, 1, 0)
  When n ← normalAt(s, p)
  Then n = vector(0, 1, 0)

Scenario: The normal on a sphere at a point on the z axis
  Given s ← sphere
   And p ← point(0, 0, 1)
  When n ← normalAt(s, p)
  Then n = vector(0, 0, 1)

Scenario: The normal on a sphere at a nonaxial point
  Given s ← sphere
   And p ← point(0.57735026919, 0.57735026919, 0.57735026919)
  When n ← normalAt(s, p)
  Then n = vector(0.57735026919, 0.57735026919, 0.57735026919)

Scenario: The normal is a normalized vector
  Given s ← sphere
   And p ← point(0.57735026919, 0.57735026919, 0.57735026919)
  When n ← normalAt(s, p)
  Then n = normalize(n)

#Scenario: Computing the normal on a translated sphere
#  Given s ← sphere
#    And set_transform(s, translation(0, 1, 0))
#  When n ← normalAt(s, point(0, 1.70711, -0.70711))
#  Then n = vector(0, 0.70711, -0.70711)
#
#Scenario: Computing the normal on a transformed sphere
#  Given s ← sphere
#    And m ← scaling(1, 0.5, 1) * rotation_z(π/5)
#    And set_transform(s, m)
#  When n ← normalAt(s, point(0, √2/2, -√2/2))
#  Then n = vector(0, 0.97014, -0.24254)

Scenario: A sphere has a default material
  Given s ← sphere
  When m ← s.material
  Then m = material

Scenario: A sphere may be assigned a material
  Given s ← sphere
    And m ← material
    And m.ambient ← 1
  When s.material ← m
  Then s.material = m

#Scenario: A helper for producing a sphere with a glassy material
#  Given s ← glass_sphere()
#  Then s.transform = identity_matrix
#    And s.material.transparency = 1.0
#    And s.material.refractive_index = 1.5
