package diagram.shapes;

import java.io.*;

public interface Shape extends Serializable {
  boolean isPointOn(int locationX, int locationY);
  
  boolean isInRegion(Rectangle region);
  
  void moveBy(int locationX, int locationY);
  
}
