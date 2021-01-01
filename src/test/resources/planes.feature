Feature: Planes

Scenario: The normal of a plane is constant everywhere
  Given pl ← plane
  When p ← point(0, 0, 0)
    And n1 ← local_normal_at(pl, p)
    And p ← point(10, 0, -10)
    And n2 ← local_normal_at(pl, p)
    And p ← point(-5, 0, 150)
    And n3 ← local_normal_at(pl, p)
  Then n1 = vector(0, 1, 0)
    And n2 = vector(0, 1, 0)
    And n3 = vector(0, 1, 0)

Scenario: Intersect with a ray parallel to the plane
  Given pl ← plane
    And origin ← point(0, 10, 0)
    And direction ← vector(0, 0, 1)
    And r ← ray(origin, direction)
  When xs ← local_intersect(p, r)
  Then xs is empty

Scenario: Intersect with a coplanar ray
  Given pl ← plane
  And origin ← point(0, 0, 0)
  And direction ← vector(0, 0, 1)
  And r ← ray(origin, direction)
  When xs ← local_intersect(p, r)
  Then xs is empty

Scenario: A ray intersecting a plane from above
  Given pl ← plane
  And origin ← point(0, 1, 0)
  And direction ← vector(0, -1, 0)
  And r ← ray(origin, direction)
  When xs ← local_intersect(p, r)
  Then xs.count = 1
    And xs[0].t = 1
    And xs[0].object = pl

Scenario: A ray intersecting a plane from below
  Given pl ← plane
  And origin ← point(0, -1, 0)
  And direction ← vector(0, 1, 0)
  And r ← ray(origin, direction)
  When xs ← local_intersect(p, r)
  Then xs.count = 1
    And xs[0].t = 1
    And xs[0].object = pl
