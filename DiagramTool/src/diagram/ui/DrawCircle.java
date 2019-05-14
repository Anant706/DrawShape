package diagram.ui;

import diagram.shapes.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;

public class DrawCircle implements DrawShape {
  
  @Override
  public void draw(GraphicsContext graphicsContext, Shape shape) {
    Circle circle = (Circle) shape;
    graphicsContext.setFill(Color.RED);
    graphicsContext.fillOval(circle.getX()-circle.getRadius(), circle.getY() - circle.getRadius(), circle.getRadius(), circle.getRadius());
  }
  
  @Override
  public Shape createShape(int posX, int posY) {
    int radius = 50;
    Circle circle = new Circle(posX , posY , radius);
    return circle;
  }
}
