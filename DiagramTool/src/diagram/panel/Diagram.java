package diagram.panel;

import diagram.shapes.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Diagram implements Serializable {
  
  private List<Shape> shapes = new ArrayList<>();
  
  public byte[] save( ) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    objectOutputStream.writeObject(this);
    
    return byteArrayOutputStream.toByteArray();
  }
  
  public static Diagram load(byte[] stream) throws IOException, ClassNotFoundException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(stream);
    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
    
    return (Diagram) objectInputStream.readObject();
  }
  
  public List<Shape> getShapes() {
    return shapes;
  }
  
  public Diagram add(Shape shape) {
    shapes.add(0, shape);
    
    return this;
  }
  
  public void deleteAt(int locationX, int locationY) {
    selectAt(locationX, locationY)
        .ifPresent(shape -> shapes.remove(shape));
  }
  
  public Optional<Shape> selectAt(int locationX, int locationY) {
    return shapes.stream()
        .filter(shape -> shape.isPointOn(locationX, locationY))
        .findFirst();
  }
  
  public void groupShapesInRegion(int locationX, int locationY, int width, int length) {
    
    List<Shape> shapesInRegion = shapes.stream()
        .filter(shape -> shape.isInRegion(new Rectangle(locationX, locationY, width, length)))
        .collect(Collectors.toList());
    
    shapes.removeAll(shapesInRegion);
    shapes.add(new Group(shapesInRegion));
  }
  
  public void ungroup(Group group) {
    if (shapes.remove(group))
      shapes.addAll(group.getShapes());
  }
  
}
