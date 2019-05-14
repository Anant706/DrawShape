package diagram.ui;

import diagram.panel.Group;
import diagram.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DrawGroup implements DrawShape {


  @Override
  public void draw(GraphicsContext g, Shape shape) {
    Group group = (Group) shape;
    List<Shape> shapes = group.getShapes();
    for (Shape shape1: shapes) {
      DrawShape drawShape = setShapeDrawInstance(shape1.getClass());
      drawShape.draw(g, shape1);
    }
  }

  @Override
  public Shape createShape(int posX, int posY) {
    return null;
  }

  private DrawShape setShapeDrawInstance(Class<? extends Shape> shape) {
    String shapeType = shape.getSimpleName();
    DrawShape drawShape = null;
    try {
      drawShape = (DrawShape) Class.forName("diagram.ui.Draw" + shapeType).getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
      System.out.println("Failed to get class instance");
    }
    return drawShape;
  }

}
