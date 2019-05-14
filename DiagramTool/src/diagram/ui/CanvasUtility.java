package diagram.ui;

import diagram.panel.Diagram;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class CanvasUtility {

  public static FlowPane getTopPanel() {
    FlowPane flow = new FlowPane();
    flow.setHgap(25);
    flow.setPadding(new Insets(5, 5, 5, 5));
    flow.setVgap(25);
    flow.setPrefWrapLength(100);
    flow.setStyle("-fx-border-width: 5px; -fx-border-color: transparent; -fx-background-color: lightgray");
    return flow;
  }

  public static FlowPane getSidePanel() {
    FlowPane shapePanel = new FlowPane();
    shapePanel.setPadding(new Insets(25, 25, 25, 5));
    shapePanel.setVgap(25);
    shapePanel.setHgap(25);
    shapePanel.setPrefWrapLength(100);
    shapePanel.setStyle("-fx-background-color: DAE6F3;");
    return shapePanel;
  }

  public static void saveDiagramToFile(File file, Diagram diagram) {
    try {
      byte[] diagramInBytes = diagram.save();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(diagramInBytes);
      fileOutputStream.close();
    } catch (IOException e) {
      System.out.println("Failed to save diagram");
    }
  }

  public static Optional<Pair<String, String>> getLocationDialogResult() {
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Shape");

    ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(createButtonType);

    GridPane gridPane = new GridPane();
    gridPane.setHgap(15);
    gridPane.setVgap(15);
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    TextField xLabel = new TextField();
    TextField yLabel = new TextField();

    gridPane.add(new Label("X Coordinate"), 0, 0);
    gridPane.add(xLabel, 1, 0);
    gridPane.add(new Label("Y Coordinate"), 0, 1);
    gridPane.add(yLabel, 1, 1);
    dialog.getDialogPane().setContent(gridPane);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == createButtonType) {
        return new Pair<>(xLabel.getText(), yLabel.getText());
      }
      return null;
    });

    return dialog.showAndWait();
  }


}
