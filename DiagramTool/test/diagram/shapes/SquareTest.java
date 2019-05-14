package diagram.shapes;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

  private Square square;

  @BeforeEach
  public void setUp() {
    square = new Square(5, 5, 10);
  }

  @Test
  public void createSquare() {
    assertEquals(5, square.getX());
    assertEquals(5, square.getY());
    assertEquals(10, square.getLength());
  }

  @Test
  public void changeLocationOfSquare() {
    square.setX(8);
    square.setY(9);

    assertEquals(8, square.getX());
    assertEquals(9, square.getY());
  }

  @Test
  public void pointIsOutsideSquare() {
    assertAll(
        () -> assertFalse(square.isPointOn(3, 5)),
        () -> assertFalse(square.isPointOn(17, 5)),
        () -> assertFalse(square.isPointOn(15, 3)),
        () -> assertFalse(square.isPointOn(15, 17))
    );
  }

  @Test
  public void pointIsInsideSquare() {
    assertAll(() -> assertTrue(square.isPointOn(6, 12)),
        () -> assertTrue(square.isPointOn(5, 6)),
        () -> assertTrue(square.isPointOn(15, 10)),
        () -> assertTrue(square.isPointOn(11, 5)),
        () -> assertTrue(square.isPointOn(10, 15))
    );
  }

  @Test
  public void isSquareInRegion() {
    assertAll(() -> assertFalse(square.isInRegion(new Rectangle(4, 4, 2, 2))),
        () -> assertFalse(square.isInRegion(new Rectangle(4, 4, 12, 5))),
        () -> assertTrue(square.isInRegion(new Rectangle(4, 4, 12, 12)))
    );
  }

}