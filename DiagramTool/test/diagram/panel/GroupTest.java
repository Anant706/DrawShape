package diagram.panel;

import diagram.shapes.*;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
  private Circle circle;
  private Rectangle rectangle;
  private Square square;
  private Group group;

  @BeforeEach
  public void setUp() {
    circle = new Circle(5, 5, 2);
    rectangle = new Rectangle(5, 5, 6, 4);
    square = new Square(5, 5, 4);
    group = new Group(List.of(circle, rectangle, square));
  }

  @Test
  public void isPointOnGroup() {
    assertAll(() -> assertTrue(group.isPointOn(5, 5)),
        () -> assertTrue(group.isPointOn(6, 6)),
        () -> assertFalse(group.isPointOn(14, 8)),
        () -> assertFalse(group.isPointOn(8, 14)),
        () -> assertFalse(group.isPointOn(1, 8)),
        () -> assertFalse(group.isPointOn(8, 1)));
  }

  @Test
  public void isGroupInRegion() {
    assertAll(() -> assertTrue(group.isInRegion(new Rectangle(3, 3, 12, 12))),
        () -> assertFalse(group.isInRegion(new Rectangle(5, 5, 7, 7))),
        () -> assertFalse(group.isInRegion(new Rectangle(11, 11, 7, 7))),
        () -> assertFalse(group.isInRegion(new Rectangle(16, 10, 7, 7))),
        () -> assertFalse(group.isInRegion(new Rectangle(2, 11, 7, 7))),
        () -> assertFalse(group.isInRegion(new Rectangle(10, 2, 7, 7))));
  }

  @Test
  public void whenMovedGroupAllItsMembersMoved() {
    group.moveBy(10, 10);
    List<Shape> shapes = group.getShapes();

    List<Shape> shapeList = shapes.stream()
        .filter(shape -> shape.isPointOn(10, 10))
        .collect(Collectors.toList());

    assertEquals(List.of(circle, rectangle, square), shapeList);
    assertEquals(10, ((Circle) shapeList.get(0)).getX());
    assertEquals(10, ((Circle) shapeList.get(0)).getY());
  }

}