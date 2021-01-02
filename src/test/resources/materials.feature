Feature: Materials

Background:
  Given m ← material
    And position ← point(0, 0, 0)
    And intensity ← color(1, 1, 1)
    And black ← color(0, 0, 0)
    And white ← color(1, 1, 1)

Scenario: The default material
  Given m ← material
  Then m.color = color(1, 1, 1)
    And m.ambient = 0.1
    And m.diffuse = 0.9
    And m.specular = 0.9
    And m.shininess = 200.0

#Scenario: Reflectivity for the default material
#  Given m ← material()
#  Then m.reflective = 0.0
#
#Scenario: Transparency and Refractive Index for the default material
#  Given m ← material()
#  Then m.transparency = 0.0
#    And m.refractive_index = 1.0

Scenario: Lighting with the eye between the light and the surface
  Given eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
    And pos ← point(0, 0, -10)
    And light ← point_light(pos, intensity)
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(1.9, 1.9, 1.9)

Scenario: Lighting with the eye between light and surface, eye offset 45°
  Given eyev ← vector(0.0, 0.70710678118, -0.70710678118)
    And normalv ← vector(0, 0, -1)
    And pos ← point(0, 0, -10)
    And light ← point_light(pos, intensity)
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(1.0, 1.0, 1.0)

Scenario: Lighting with eye opposite surface, light offset 45°
  Given eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
  And pos ← point(0, 10, -10)
  And light ← point_light(pos, intensity)
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(0.7364, 0.7364, 0.7364)

Scenario: Lighting with eye in the path of the reflection vector
  Given eyev ← vector(0.0, -0.70710678118, -0.70710678118)
    And normalv ← vector(0, 0, -1)
  And pos ← point(0, 10, -10)
  And light ← point_light(pos, intensity)
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(1.6364, 1.6364, 1.6364)

Scenario: Lighting with the light behind the surface
  Given eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
  And pos ← point(0, 0, 10)
  And light ← point_light(pos, intensity)
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(0.1, 0.1, 0.1)

Scenario: Lighting with the surface in shadow
  Given eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
    And pos ← point(0, 0, -10)
    And light ← point_light(pos, intensity)
    And in_shadow ← true
  When result ← lighting(m, light, position, eyev, normalv, in_shadow)
  Then result = color(0.1, 0.1, 0.1)

Scenario: Lighting with a pattern applied
  Given m.pattern ← stripe_pattern(white, black)
    And m.ambient ← 1.0
    And m.diffuse ← 0.0
    And m.specular ← 0.0
    And eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
    And position ← point(0, 0, -10)
    And light ← point_light(position, intensity)
    And p1 ← point(0.9, 0.0, 0.0)
    And p2 ← point(1.1, 0.0, 0.0)
  When c1 ← lighting(m, light, p1, eyev, normalv, false)
    And c2 ← lighting(m, light, p2, eyev, normalv, false)
  Then c1 = color(1, 1, 1)
    And c2 = color(0, 0, 0)
