package diagram.shapes;

public class Rectangle implements Shape {
  
  private static final long serialVersionUID = 1L;
  private int x;
  private int y;
  private int length;
  private int width;
  
  public Rectangle(int locationX, int locationY, int recWidth, int recLength) {
    x = locationX;
    y = locationY;
    width = recWidth;
    length = recLength;
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
  
  public int getLength() {
    return length;
  }
  
  public int getWidth() {
    return width;
  }
  
  @Override
  public boolean isPointOn(int locationX, int locationY) {
    
    return locationX >= x && locationX <= x + width && locationY >= y && locationY <= y + length;
  }
  
  @Override
  public boolean isInRegion(Rectangle region) {
    return region.isPointOn(x, y) &&
        region.isPointOn(x + width, y) &&
        region.isPointOn(x, y + length);
  }
  
  @Override
  public void moveBy(int locationX, int locationY) {
    x = locationX;
    y = locationY;
  }
  
}