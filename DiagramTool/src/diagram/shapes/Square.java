package diagram.shapes;

public class Square implements Shape {
  
  private static final long serialVersionUID = 1L;
  private int x;
  private int y;
  private int length;
  
  public Square(int locationX, int locationY, int side) {
    x = locationX;
    y = locationY;
    length = side;
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
  
  public boolean isPointOn(int locationX, int locationY) {
    
    return locationX >= x && locationX <= x + length && locationY >= y && locationY <= y + length;
  }
  
  @Override
  public boolean isInRegion(Rectangle region) {
    return region.isPointOn(x, y) &&
        region.isPointOn(x + length, y) &&
        region.isPointOn(x, y + length);
  }
  
  @Override
  public void moveBy(int locationX, int locationY) {
    x = locationX;
    y = locationY;
  }
}