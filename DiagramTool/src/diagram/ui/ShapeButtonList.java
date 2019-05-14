package diagram.ui;

import javafx.scene.*;
import javafx.scene.control.*;

import java.util.*;

public class ShapeButtonList {
  
  private List<Node> shapeList = new ArrayList<>();
  
  private DrawShape drawShape;
  
  public List<Node> getShapeList(DrawShape shape) {
    drawShape = shape;
    shapeList.add(getCircleButton());
    shapeList.add(getRectangleButton());
    shapeList.add(getSquareButton());
    return shapeList;
  }
  
  public Button getCircleButton() {
    Button circleButton = new Button("Circle");
    circleButton.setOnAction(actionEvent -> drawShape = new DrawCircle());
    return circleButton;
  }
  
  private Button getRectangleButton() {
    Button rectangleButton = new Button("Rectangle");
    rectangleButton.setOnAction(actionEvent -> drawShape = new DrawRectangle());
    return rectangleButton;
  }
  
  private Button getSquareButton() {
    Button squareButton = new Button("Square");
    squareButton.setOnAction(actionEvent -> drawShape = new DrawSquare());
    return squareButton;
  }
}
