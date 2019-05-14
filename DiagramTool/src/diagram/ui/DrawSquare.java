package diagram.ui;

import diagram.shapes.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;

public class DrawSquare implements DrawShape {
  
  @Override
  public void draw(GraphicsContext graphicsContext, Shape shape) {
    Square square = (Square) shape;
    graphicsContext.setFill(Color.BROWN);
    graphicsContext.fillRect(square.getX(), square.getY(), square.getLength(), square.getLength());
  }
  
  @Override
  public Shape createShape(int posX, int posY) {
    Square square = new Square(posX, posY, 50);
    return square;
  }
  
}
