package diagram.panel;

import diagram.shapes.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DiagramTest {
  
  private Diagram diagram;
  private Circle circle;
  private Rectangle rectangle;
  private Square square;
  
  @BeforeEach
  public void setUp() {
    diagram = new Diagram();
    circle = new Circle(5, 5, 2);
    rectangle = new Rectangle(5, 5, 6, 4);
    square = new Square(5, 5, 4);
  }
  
  @Test
  public void diagramHasNothingInIt() {
    assertTrue(diagram.getShapes().isEmpty());
  }
  
  @Test
  public void addOneCircleToDiagram() {
    diagram.add(circle);
    
    assertEquals(List.of(circle), diagram.getShapes());
  }
  
  @Test
  public void addTwoCircleToDiagram() {
    Circle circle2 = new Circle(6, 6, 5);
    diagram.add(circle)
        .add(circle2);
    
    assertEquals(List.of(circle2, circle), diagram.getShapes());
  }
  
  @Test
  public void addOneCircleOneRectangleToDiagram() {
    diagram.add(circle)
        .add(rectangle);
    
    assertEquals(List.of(rectangle, circle), diagram.getShapes());
  }
  
  @Test
  public void addOneCircleOneRectangleOneSquareToDiagram() {
    diagram.add(circle)
        .add(rectangle)
        .add(square);
    
    assertEquals(List.of(square, rectangle, circle), diagram.getShapes());
  }
  
  @Test
  public void deleteAtWhenDiagramHasNothing() {
    diagram.deleteAt(0, 0);
    
    assertEquals(0, diagram.getShapes().size());
  }
  
  @Test
  public void diagramWithTwoCirclesDeletesNothingWhenLocationIsNotWithinEitherCircle() {
    Circle circle1 = new Circle(10, 10, 2);
    Circle circle2 = new Circle(11, 11, 2);
    diagram.add(circle1)
        .add(circle2);
    
    diagram.deleteAt(3, 3);
    
    assertEquals(List.of(circle2, circle1), diagram.getShapes());
  }
  
  @Test
  public void diagramWithTwoCirclesAtSameLocationDeletesTopCircleForLocation() {
    Circle topCircle = new Circle(5, 5, 10);
    Circle bottomCircle = new Circle(5, 5, 5);
    diagram.add(bottomCircle)
        .add(topCircle);
    
    diagram.deleteAt(1, 1);
    
    assertEquals(List.of(bottomCircle), diagram.getShapes());
  }
  
  @Test
  public void diagramWithTwoCirclesAtDifferentLocationDeleteSecondCircleWhenLocationPointsToSecondCircle() {
    Circle bottomCircle = new Circle(5, 5, 2);
    Circle topCircle = new Circle(10, 10, 12);
    diagram.add(bottomCircle)
        .add(topCircle);
    
    diagram.deleteAt(1, 1);
    
    assertEquals(List.of(bottomCircle), diagram.getShapes());
  }
  
  @Test
  public void diagramWithCircleAndRectangleWhereCircleIsSmallerAndOnTopOfRectangleDeleteCircleWhenLocationIsWithinCircle() {
    Rectangle bottomRectangle = new Rectangle(5, 5, 10, 9);
    Circle topCircle = new Circle(10, 10, 2);
    diagram.add(bottomRectangle)
        .add(topCircle);
    
    diagram.deleteAt(10, 11);
    
    assertEquals(List.of(bottomRectangle), diagram.getShapes());
  }
  
  @Test
  public void diagramWithCircleAndRectangleWhereCircleIsSmallerAndOnTopOfRectangleDeleteRectangleWhenLocationIsWithinRectangleButOutsideCircle() {
    Rectangle topRectangle = new Rectangle(5, 5, 10, 9);
    Circle bottomCircle = new Circle(10, 10, 2);
    diagram.add(bottomCircle)
        .add(topRectangle);
    
    diagram.deleteAt(12, 7);
    
    assertEquals(List.of(bottomCircle), diagram.getShapes());
  }
  
  @Test
  public void nothingGetsSelectedIfDiagramHasNothing() {
    assertTrue(diagram.selectAt(5, 10).isEmpty());
  }
  
  @Test
  public void diagramWithTwoCirclesSelectsNothingWhenLocationIsNotWithinEitherCircles() {
    Circle circle1 = new Circle(10, 10, 2);
    Circle circle2 = new Circle(11, 11, 2);
    diagram.add(circle1)
        .add(circle2);
    
    Optional<Shape> shape = diagram.selectAt(3, 3);
    
    assertTrue(shape.isEmpty());
  }
  
  @Test
  public void diagramWithTwoCirclesAtDifferentLocationSelectsFirstCircleWhenLocationPointsToFirstCircle() {
    Circle secondCircle = new Circle(10, 10, 7);
    Circle firstCircle = new Circle(5, 5, 5);
    diagram.add(secondCircle)
        .add(firstCircle);
    
    Optional<Shape> shape = diagram.selectAt(3, 3);
    
    assertTrue(shape.isPresent());
    assertEquals(firstCircle, shape.get());
  }
  
  @Test
  public void diagramWithTwoCirclesAtDifferentLocationSelectsSecondCircleWhenLocationPointsToSecondCircle() {
    Circle secondCircle = new Circle(10, 10, 10);
    Circle firstCircle = new Circle(5, 5, 5);
    diagram.add(secondCircle)
        .add(firstCircle);
    
    Optional<Shape> shape = diagram.selectAt(11, 11);
    
    assertTrue(shape.isPresent());
    assertEquals(secondCircle, shape.get());
  }
  
  @Test
  public void diagramWithTwoCirclesAtSameLocationSelectsCircleOnTopForLocationWithinCircle() {
    Circle bottomCircle = new Circle(5, 5, 10);
    Circle topCircle = new Circle(5, 5, 5);
    diagram.add(bottomCircle)
        .add(topCircle);
    
    Optional<Shape> shape = diagram.selectAt(6, 6);
    
    assertTrue(shape.isPresent());
    assertEquals(topCircle, shape.get());
  }
  
  @Test
  public void circleIsSelectedWhenLocationIsWithinCircleInADiagramWithSmallCircleOnTopOfRectangle() {
    Rectangle bottomRectangle = new Rectangle(5, 5, 10, 9);
    Circle topCircle = new Circle(10, 10, 2);
    diagram.add(bottomRectangle)
        .add(topCircle);
    
    Optional<Shape> shape = diagram.selectAt(10, 11);
    
    assertTrue(shape.isPresent());
    assertEquals(topCircle, shape.get());
  }
  
  @Test
  public void rectangleIsSelectedWhenLocationIsWithinRectangleButOutsideCircleInADiagramWithSmallCircleOnTopOfRectangle() {
    Rectangle bottomRectangle = new Rectangle(5, 5, 10, 9);
    Circle topCircle = new Circle(10, 10, 2);
    diagram.add(bottomRectangle)
        .add(topCircle);
    
    Optional<Shape> shape = diagram.selectAt(6, 6);
    
    assertTrue(shape.isPresent());
    assertEquals(bottomRectangle, shape.get());
  }
  
  @Test
  public void groupAllShapesWithinARectangularRegion() {
    diagram.add(circle)
        .add(rectangle)
        .add(square);
    
    diagram.groupShapesInRegion(2, 2, 10, 8);
    
    assertTrue(diagram.getShapes().get(0) instanceof Group);
  }
  
  @Test
  public void diagramWithShapesTurnsIntoDiagramWithGroupWhenAllShapesAreGrouped() {
    diagram.add(circle)
        .add(rectangle)
        .add(square);
    
    diagram.groupShapesInRegion(2, 2, 10, 8);
    
    assertFalse(diagram.getShapes().isEmpty());
    assertTrue(diagram.getShapes().get(0) instanceof Group);
  }
  
  @Test
  public void diagramWithShapesTurnsIntoDiagramWithGroupAndFewShapesWhenOnlyFewShapesAreGrouped() {
    Circle circle = new Circle(5, 5, 2);
    Rectangle rectangle = new Rectangle(5, 5, 6, 4);
    Square square = new Square(12, 12, 1);
    diagram.add(circle)
        .add(rectangle)
        .add(square);
    
    diagram.groupShapesInRegion(2, 2, 10, 8);
    
    List<Shape> shapes = diagram.getShapes();
    
    assertEquals(square, shapes.get(0));
    assertTrue(shapes.get(1) instanceof Group);
  }
  
  @Test
  public void ungroupingAGroupReplacesGroupWithinDiagramWithShapesInTheGroup() {
    diagram.add(circle)
        .add(rectangle)
        .add(square);
    diagram.groupShapesInRegion(2, 2, 10, 8);
    
    diagram.ungroup((Group) diagram.getShapes().get(0));
    
    List<Shape> shapes = diagram.getShapes();
    assertFalse(diagram.getShapes().isEmpty());
    assertEquals(List.of(square, rectangle, circle), shapes);
  }
  
  @Test
  public void deleteAGroupAndConfirmThatDiagramDoesNotHaveGroupNorShapesThatWereInGroup() {
    diagram.add(circle)
        .add(rectangle)
        .add(square);
    
    diagram.groupShapesInRegion(2, 2, 10, 8);
    
    diagram.deleteAt(5, 5);
    assertEquals(0, diagram.getShapes().size());
  }
  
  @Test
  public void groupTwoGroupsAndCircleIntoAGroup() {
    Circle circle1 = new Circle(5, 5, 1);
    Square square1 = new Square(5, 5, 2);
    Circle circle2 = new Circle(15, 15, 1);
    Square square2 = new Square(15, 15, 2);
    Circle circle3 = new Circle(20, 20, 1);
    
    diagram.add(circle1)
        .add(square1)
        .add(circle2)
        .add(square2)
        .add(circle3);
    
    diagram.groupShapesInRegion(3, 3, 5, 5);
    diagram.groupShapesInRegion(13, 13, 5, 5);
    diagram.groupShapesInRegion(2, 2, 25, 25);
    
    assertTrue(diagram.getShapes().get(0) instanceof Group);
  }
  
  @Test
  public void ungroupANonExistentGroupFromADiagram() {
    Group group = new Group(new ArrayList<>(
        List.of(new Circle(10, 10, 5),
            new Circle(20, 20, 5))));
    
    diagram.ungroup(group);
    
    assertTrue(diagram.getShapes().isEmpty());
  }
  
  @Test
  public void saveAndLoadDiagramWithNothingInIt() throws IOException, ClassNotFoundException {
    
    byte[] stream = new Diagram().save();
    
    Diagram deserializeDiagram = Diagram.load(stream);
    
    assertEquals(List.of(), deserializeDiagram.getShapes());
  }
  
  @Test
  public void saveAndLoadDiagramWithOneCircleInIt() throws IOException, ClassNotFoundException {
    diagram.add(circle);
    
    byte[] stream = diagram.save();
    
    Diagram deserializeDiagram = Diagram.load(stream);
    
    assertTrue(deserializeDiagram.getShapes().get(0) instanceof Circle);
  }
  
  @Test
  public void saveAndLoadDiagramWithOneCircleOneRectangleInIt() throws IOException, ClassNotFoundException {
    diagram.add(circle)
        .add(rectangle);
    
    byte[] stream = diagram.save();
    
    Diagram deserializeDiagram = Diagram.load(stream);
    
    assertTrue(deserializeDiagram.getShapes().get(0) instanceof Rectangle);
    assertTrue(deserializeDiagram.getShapes().get(1) instanceof Circle);
  }
}