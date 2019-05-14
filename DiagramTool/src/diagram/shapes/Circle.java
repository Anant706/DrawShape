package diagram.shapes;

public class Circle implements Shape {
  
  private static final long serialVersionUID = 1L;
  private int x;
  private int y;
  private int radius;
  
  public Circle(int centerX, int centerY, int radiusLength) {
    x = centerX;
    y = centerY;
    radius = radiusLength;
  }
  
  public int getX() {
    return x;
  }
  
  public void setX(int locationX) {
    x = locationX;
  }
  
  public int getY() {
    return y;
  }
  
  public void setY(int locationY) {
    y = locationY;
  }
  
  public int getRadius() {
    return radius;
  }
  
  @Override
  public boolean isPointOn(int locationX, int locationY) {
    return (int) Math.sqrt(Math.pow(x - locationX, 2) + Math.pow(y - locationY, 2)) <= radius;
  }
  
  @Override
  public boolean isInRegion(Rectangle region) {
    return region.isPointOn(x - radius, y) &&
        region.isPointOn(x, y - radius) &&
        region.isPointOn(x, y + radius) &&
        region.isPointOn(x + radius, y);
  }
  
  @Override
  public void moveBy(int locationX, int locationY) {
    x = locationX;
    y = locationY;
  }
  
}
