package diagram.ui;

import diagram.shapes.*;
import javafx.scene.canvas.*;

public interface DrawShape {
  
  void draw(GraphicsContext g, Shape shape);
  
  Shape createShape(int posX, int posY);
}
