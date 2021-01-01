Feature: Camera

Scenario: Constructing a camera
  Given hsize ← 160
    And vsize ← 120
    And field_of_view ← 1.57079632679
  When c ← camera(hsize, vsize, field_of_view)
  Then c.hsize = 160
    And c.vsize = 120
    And c.field_of_view = 1.57079632679
    And c.transform = identity_matrix

Scenario: The pixel size for a horizontal canvas
  Given c ← camera(200, 125, 1.57079632679)
  Then c.pixel_size = 0.01

Scenario: The pixel size for a vertical canvas
  Given c ← camera(125, 200, 1.57079632679)
  Then c.pixel_size = 0.01

Scenario: Constructing a ray through the center of the canvas
  Given c ← camera(201, 101, 1.57079632679)
  When r ← ray_for_pixel(c, 100, 50)
  Then r.origin = point(0, 0, 0)
    And r.direction = vector(0, 0, -1)

Scenario: Constructing a ray through a corner of the canvas
  Given c ← camera(201, 101, 1.57079632679)
  When r ← ray_for_pixel(c, 0, 0)
  Then r.origin = point(0, 0, 0)
    And r.direction = vector(0.66519, 0.33259, -0.66851)

Scenario: Constructing a ray when the camera is transformed
  Given c ← camera(201, 101, 1.57079632679)
  When c.transform ← rotation_y(0.78539816339) * translation(0, -2, 5)
    And r ← ray_for_pixel(c, 100, 50)
  Then r.origin = point(0, 2, -5)
    And r.direction = vector(0.70710678118, 0.0, -0.70710678118)

Scenario: Rendering a world with a camera
  Given w ← defaultWorld
    And c ← camera(11, 11, 1.57079632679)
    And from ← point(0, 0, -5)
    And to ← point(0, 0, 0)
    And up ← vector(0, 1, 0)
    And c.transform ← view_transform(from, to, up)
  When image ← render(c, w)
  Then pixel_at(image, 5, 5) = color(0.38066, 0.47583, 0.2855)
