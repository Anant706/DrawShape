package diagram.panel;

import diagram.shapes.*;

import java.util.*;

public class Group implements Shape {
  
  private List<Shape> shapes = new ArrayList<>();
  
  public Group(List<Shape> shapesToGroup) {
    shapes.addAll(shapesToGroup);
  }
  
  public List<Shape> getShapes() {
    return shapes;
  }
  
  @Override
  public boolean isPointOn(int locationX, int locationY) {
    return shapes.stream()
        .anyMatch(shape -> shape.isPointOn(locationX, locationY));
  }
  
  public boolean isInRegion(Rectangle region) {
    return shapes.stream()
        .allMatch(shape -> shape.isInRegion(region));
  }
  
  @Override
  public void moveBy(int locationX, int locationY) {
    shapes.forEach(shape -> shape.moveBy(locationX, locationY));
  }
  
}
