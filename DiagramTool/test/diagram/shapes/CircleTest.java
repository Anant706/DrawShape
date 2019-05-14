package diagram.shapes;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

  private Circle circle;

  @BeforeEach
  public void setUp() {
    circle = new Circle(5, 5, 5);
  }

  @Test
  public void canaryTest() {
    assertTrue(true);
  }

  @Test
  public void createCircle() {
    assertEquals(5, circle.getX());
    assertEquals(5, circle.getY());
    assertEquals(5, circle.getRadius());
  }

  @Test
  public void changeLocationOfCircle() {
    circle.setX(8);
    circle.setY(9);

    assertEquals(8, circle.getX());
    assertEquals(9, circle.getY());
  }

  @Test
  public void pointIsOutsideCircleReturnFalse() {
    assertAll(() -> assertFalse(circle.isPointOn(5, -1)),
        () -> assertFalse(circle.isPointOn(5, 11)),
        () -> assertFalse(circle.isPointOn(-1, 5)),
        () -> assertFalse(circle.isPointOn(11, 5)),
        () -> assertTrue(circle.isPointOn(3, 3)),
        () -> assertTrue(circle.isPointOn(10, 7))
    );
  }

  @Test
  public void isCircleInRegion() {
    assertAll(() -> assertFalse(circle.isInRegion(new Rectangle(0, 3, 10, 10))),
        () -> assertTrue(circle.isInRegion(new Rectangle(0, 0, 10, 10))),
        () -> assertFalse(circle.isInRegion(new Rectangle(0, 0, 8, 8))),
        () -> assertFalse(circle.isInRegion(new Rectangle(0, 0, 8, 10)))
    );
  }

}