package diagram.ui;

import diagram.panel.Diagram;
import diagram.shapes.Shape;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CanvasUI extends Application {

  private final int CANVAS_WIDTH = 800, CANVAS_LENGTH = 800;
  private Optional<Shape> selectedShape;
  private Diagram diagram = new Diagram();
  private DrawShape drawShape;
  private GraphicsContext graphicsContext;
  private double previousX;
  private double previousY;
  private boolean isGroupDrawn;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    StackPane stackPane = new StackPane();
    Canvas canvas = createCanvas();

    stackPane.getChildren().add(canvas);
    BorderPane borderPane = new BorderPane(stackPane);
    borderPane.setLeft(addSidePanel());
    borderPane.setTop(addTopPanel(stage));

    Scene scene = new Scene(borderPane, CANVAS_WIDTH, CANVAS_LENGTH);
    stage.setScene(scene);
    stage.show();
  }

  private Canvas createCanvas() {

    Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_LENGTH);
    graphicsContext = canvas.getGraphicsContext2D();
    graphicsContext.setLineWidth(2.0);
    graphicsContext.setFill(Color.RED);
    canvas.setOnMouseClicked(this::canvasEventHandler);

    canvas.setOnMousePressed(event -> {
      selectedShape = diagram.selectAt((int) event.getX(), (int) event.getY());
      if (isGroupDrawn) {
        previousX = event.getX();
        previousY = event.getY();
        graphicsContext.fillRect(event.getX(), event.getY(), 0, 0);
      }
    });

    canvas.setOnMouseReleased(event -> {
      int x = (int) event.getX();
      int y = (int) event.getY();
      if (selectedShape.isPresent()) {
        selectedShape.get().moveBy(x, y);
        redrawDiagramOnCanvas();
      }

      if (isGroupDrawn) {
        graphicsContext.clearRect(previousX, previousY, 0, 0);
        int currentX = (int) Math.min(previousX, event.getX());
        int currentY = (int) Math.min(previousY, event.getY());
        int width = (int) Math.abs(event.getX() - previousX);
        int height = (int) Math.abs(event.getY() - previousY);
        graphicsContext.setFill(Color.rgb(50, 50, 50, .3));
        graphicsContext.fillRect(currentX, currentY, width, height);
        diagram.groupShapesInRegion(currentX, currentY, width, height);
        isGroupDrawn = false;
      }

    });

    return canvas;
  }

  private void canvasEventHandler(MouseEvent mouseEvent) {
    boolean isSecondaryAction = mouseEvent.getButton() == MouseButton.SECONDARY;
    if (isSecondaryAction) {
        deleteShape((int) mouseEvent.getX(), (int) mouseEvent.getY());
    }
  }

  private FlowPane addSidePanel() {
    FlowPane shapePanel = CanvasUtility.getSidePanel();
    shapePanel.getChildren().addAll(addButtons());
    return shapePanel;
  }

  private FlowPane addTopPanel(Stage stage) {
    FlowPane panel = CanvasUtility.getTopPanel();
    panel.getChildren().addAll(getSaveButton(stage), getLoadButton(stage), getCreateGroupButton());
    panel.getChildren().addAll( new Label("Press Right Click on Shape to Delete"));
    return panel;
  }

  private Button getLoadButton(Stage stage) {
    Button loadButton = new Button();
    loadButton.setText("Load");
    loadButton.setOnAction(actionEvent -> {
      FileChooser fileChooser = new FileChooser();
      File file = fileChooser.showOpenDialog(stage);

      if (file != null) {
        loadDiagramFromFile(file);
      }
    });
    return loadButton;
  }

  private void loadDiagramFromFile(File file) {
    try {
      FileInputStream fileInputStream = new FileInputStream(file);
      byte[] diagramInBytes = new byte[(int) file.length()];
      fileInputStream.read(diagramInBytes);
      fileInputStream.close();
      redrawDiagram(Diagram.load(diagramInBytes));

    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Failed to load diagram");
    }
  }

  private void redrawDiagram(Diagram newDiagram) {
    diagram = newDiagram;
    redrawDiagramOnCanvas();
  }

  private void redrawDiagramOnCanvas() {
    graphicsContext.clearRect(0, 0, CANVAS_WIDTH, CANVAS_LENGTH);
    for (Shape shape: diagram.getShapes()) {
      setShapeDrawInstance(shape.getClass());
      drawShape.draw(graphicsContext, shape);
    }
  }

  private void setShapeDrawInstance(Class<? extends Shape> shape) {
    String shapeType = shape.getSimpleName();
    try {
      drawShape = (DrawShape) Class.forName("diagram.ui.Draw" + shapeType).getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
      System.out.println("Failed to get class instance");
    }
  }

  private void addShape(int posX, int posY) {
    if (drawShape != null) {
      Shape shape = drawShape.createShape(posX, posY);
      drawShape.draw(graphicsContext, shape);
      diagram.add(shape);
    }
  }

  private Button getSaveButton(Stage stage) {
    Button saveButton = new Button();
    saveButton.setText("Save");
    saveButton.setOnAction(actionEvent -> {
      FileChooser fileChooser = new FileChooser();
      File file = fileChooser.showSaveDialog(stage);

      if (file != null) {
        CanvasUtility.saveDiagramToFile(file, diagram);
      }
    });
    return saveButton;
  }

  private Button getCreateGroupButton() {
    Button createGroupButton = new Button();
    createGroupButton.setText("Create Group");
    createGroupButton.setOnAction(actionEvent -> isGroupDrawn = true);
    return createGroupButton;
  }


  private List<Node> addButtons() {
    List<Node> buttons = new ArrayList<>();
    Reflections reflections = new Reflections("diagram.shapes");
    Set<Class<? extends Shape>> subTypes = reflections.getSubTypesOf(Shape.class);
    for (Class<? extends Shape> type: subTypes) {
      Button button = new Button(type.getSimpleName());
      button.setOnAction(actionEvent -> handleButtonClick(type));
      buttons.add(button);
    }
    return buttons;
  }

  private void handleButtonClick(Class<? extends Shape> type) {
    setShapeDrawInstance(type);
    Optional<Pair<String, String>> input = CanvasUtility.getLocationDialogResult();
    input.ifPresent(pair -> validateInputValues(pair.getKey(), pair.getValue()));

  }

  private void validateInputValues(String x, String y) {
    try {
      int locationX = Integer.valueOf(x);
      int locationY = Integer.valueOf(y);
      addShape(locationX, locationY);
    } catch (NumberFormatException e) {
      System.out.println("Error!! Please enter integer value");
    }
  }

  private void deleteShape(int x, int y) {
    diagram.deleteAt(x, y);
    redrawDiagramOnCanvas();
  }
}
