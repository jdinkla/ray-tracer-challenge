Feature: Patterns

Background:
  Given black ← color(0, 0, 0)
    And white ← color(1, 1, 1)

Scenario: Creating a stripe pattern
  Given pattern ← stripe_pattern(white, black)
  Then pattern.a = white
    And pattern.b = black

Scenario: A stripe pattern is constant in y
  Given pattern ← stripe_pattern(white, black)
    And p1 ← point(0, 0, 0)
    And p2 ← point(0, 1, 0)
    And p3 ← point(0, 2, 0)
  Then stripe_at(pattern, p1) = white
    And stripe_at(pattern, p2) = white
    And stripe_at(pattern, p3) = white

Scenario: A stripe pattern is constant in z
  Given pattern ← stripe_pattern(white, black)
    And p1 ← point(0, 0, 0)
    And p2 ← point(0, 0, 1)
    And p3 ← point(0, 0, 2)
  Then stripe_at(pattern, p1) = white
    And stripe_at(pattern, p2) = white
    And stripe_at(pattern, p3) = white

Scenario: A stripe pattern alternates in x
  Given pattern ← stripe_pattern(white, black)
    And p1 ← point(0, 0, 0)
    And p2 ← point(0.9, 0.0, 0.0)
    And p3 ← point(1, 0, 0)
    And p4 ← point(-0.1, 0.0, 0.0)
    And p5 ← point(-1, 0, 0)
    And p6 ← point(-1.1, 0.0, 0.0)
  Then stripe_at(pattern, p1) = white
    And stripe_at(pattern, p2) = white
    And stripe_at(pattern, p3) = black
    And stripe_at(pattern, p4) = black
    And stripe_at(pattern, p5) = black
    And stripe_at(pattern, p6) = white

Scenario: Stripes with an object transformation
  Given object ← sphere
    And transform ← scaling(2, 2, 2)
    And set_transform(object, transform)
    And pattern ← stripe_pattern(white, black)
    And p1 ← point(1.5, 0, 0)
  When c ← stripe_at_object(pattern, object, p1)
  Then c = white

Scenario: Stripes with a pattern transformation
  Given object ← sphere
    And pattern ← stripe_pattern(white, black)
    And transform ← scaling(2, 2, 2)
    And set_pattern_transform(pattern, transform)
  And p1 ← point(1.5, 0, 0)
  When c ← stripe_at_object(pattern, object, p1)
  Then c = white

Scenario: Stripes with both an object and a pattern transformation
  Given object ← sphere
    And transform ← scaling(2, 2, 2)
    And set_transform(object, transform)
    And pattern ← stripe_pattern(white, black)
    And t ← translation(0.5, 0.0, 0.0)
    And set_pattern_transform(pattern, t)
  And p1 ← point(2.5, 0, 0)
  When c ← stripe_at_object(pattern, object, p1)
  Then c = white

Scenario: The default pattern transformation
  Given pattern ← test_pattern
  Then pattern.transform = identity_matrix

Scenario: Assigning a transformation
  Given pattern ← test_pattern
    And transform ← translation(1, 2, 3)
  When set_pattern_transform(pattern, transform)
  Then pattern.transform = transform

Scenario: A pattern with an object transformation
  Given shape ← sphere
    And transform ← scaling(2, 2, 2)
    And set_transform(shape, transform)
    And pattern ← test_pattern
    And p1 ← point(2, 3, 4)
  When c ← pattern_at_shape(pattern, shape, p1)
  Then c = color(1.0, 1.5, 2.0)

Scenario: A pattern with a pattern transformation
  Given shape ← sphere
    And pattern ← test_pattern
    And t ← scaling(2, 2, 2)
    And set_pattern_transform(pattern, t)
  And p1 ← point(2, 3, 4)
  When c ← pattern_at_shape(pattern, shape, p1)
  Then c = color(1.0, 1.5, 2.0)

Scenario: A pattern with both an object and a pattern transformation
  Given shape ← sphere
    And transform ← scaling(2, 2, 2)
    And set_transform(shape, transform)
    And pattern ← test_pattern
    And t ← translation(0.5, 1, 1.5)
    And set_pattern_transform(pattern, t)
  And p1 ← point(2.5, 3.0, 3.5)
  When c ← pattern_at_shape(pattern, shape, p1)
  Then c = color(0.75, 0.5, 0.25)

Scenario: A gradient linearly interpolates between colors
  Given pattern ← gradient_pattern(white, black)
    And p1 ← point(0, 0, 0)
    And p2 ← point(0.25, 0.0, 0.0)
    And p3 ← point(0.5, 0.0, 0.0)
    And p4 ← point(0.75, 0.0, 0.0)
  Then pattern_at(pattern, p1) = white
    And pattern_at(pattern, p2) = color(0.75, 0.75, 0.75)
    And pattern_at(pattern, p3) = color(0.5, 0.5, 0.5)
    And pattern_at(pattern, p4) = color(0.25, 0.25, 0.25)

Scenario: A ring should extend in both x and z
  Given pattern ← ring_pattern(white, black)
  And p1 ← point(0, 0, 0)
  And p2 ← point(1, 0, 0)
  And p3 ← point(0, 0, 1)
  And p4 ← point(0.708, 0, 0.708)
  Then pattern_at(pattern, p1) = white
    And pattern_at(pattern, p2) = black
    And pattern_at(pattern, p3) = black
    # 0.708 = just slightly more than √2/2
    And pattern_at(pattern, p4) = black

#Scenario: Checkers should repeat in x
#  Given pattern ← checkers_pattern(white, black)
#  Then pattern_at(pattern, point(0, 0, 0)) = white
#    And pattern_at(pattern, point(0.99, 0, 0)) = white
#    And pattern_at(pattern, point(1.01, 0, 0)) = black
#
#Scenario: Checkers should repeat in y
#  Given pattern ← checkers_pattern(white, black)
#  Then pattern_at(pattern, point(0, 0, 0)) = white
#    And pattern_at(pattern, point(0, 0.99, 0)) = white
#    And pattern_at(pattern, point(0, 1.01, 0)) = black
#
#Scenario: Checkers should repeat in z
#  Given pattern ← checkers_pattern(white, black)
#  Then pattern_at(pattern, point(0, 0, 0)) = white
#    And pattern_at(pattern, point(0, 0, 0.99)) = white
#    And pattern_at(pattern, point(0, 0, 1.01)) = black
