package diagram.shapes;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

  private Rectangle rectangle;

  @BeforeEach
  public void setUp() {
    rectangle = new Rectangle(5, 5, 6, 4);
  }

  @Test
  public void createRectangle() {
    assertEquals(5, rectangle.getX());
    assertEquals(5, rectangle.getY());
    assertEquals(6, rectangle.getWidth());
    assertEquals(4, rectangle.getLength());
  }

  @Test
  public void changeLocationOfRectangle() {

    rectangle.setX(8);
    rectangle.setY(9);

    assertEquals(8, rectangle.getX());
    assertEquals(9, rectangle.getY());
  }

  @Test
  public void pointIsOutsideRectangle() {
    assertAll(() -> assertFalse(rectangle.isPointOn(5, 4)),
        () -> assertFalse(rectangle.isPointOn(5, 10)),
        () -> assertFalse(rectangle.isPointOn(3, 9)),
        () -> assertFalse(rectangle.isPointOn(12, 8))
    );

  }

  @Test
  public void pointIsInsideRectangle() {
    assertAll(() -> assertTrue(rectangle.isPointOn(7, 7)),
        () -> assertTrue(rectangle.isPointOn(5, 7)),
        () -> assertTrue(rectangle.isPointOn(11, 7)),
        () -> assertTrue(rectangle.isPointOn(8, 5)),
        () -> assertTrue(rectangle.isPointOn(8, 9))
    );
  }

  @Test
  public void isRectangleInRegion() {

    assertAll(() -> assertFalse(rectangle.isInRegion(new Rectangle(4, 4, 2, 2))),
        () -> assertFalse(rectangle.isInRegion(new Rectangle(4, 4, 12, 4))),
        () -> assertTrue(rectangle.isInRegion(new Rectangle(4, 4, 7, 5))),
        () -> assertFalse(rectangle.isInRegion(new Rectangle(6, 6, 10, 10)))
    );
  }

}