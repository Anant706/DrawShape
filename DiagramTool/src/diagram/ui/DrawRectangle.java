package diagram.ui;

import diagram.shapes.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;

public class DrawRectangle implements DrawShape {
  
  @Override
  public void draw(GraphicsContext graphicsContext, Shape shape) {
    Rectangle rectangle = (Rectangle) shape;
    graphicsContext.setFill(Color.ORANGE);
    graphicsContext.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getLength());
  }
  
  @Override
  public Shape createShape(int posX, int posY) {
    Rectangle rectangle = new Rectangle(posX, posY, 50, 100);
    return rectangle;
  }
  
}
