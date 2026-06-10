package com.comp301.a09akari.view;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import com.comp301.a09akari.model.Puzzle;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class PuzzleView implements FXComponent, ModelObserver {
  private final Model model;
  private final GridPane gridPane;

  public PuzzleView(Model model) {
    this.model = model;
    this.gridPane = new GridPane();
    this.model.addObserver(this);

    // Remove gaps between cells
    gridPane.setHgap(0);
    gridPane.setVgap(0);
  }

  @Override
  public void update(Model model) {
    Platform.runLater(this::render);
  }

  @Override
  public GridPane render() {
    Puzzle puzzle = model.getActivePuzzle();
    int width = puzzle.getWidth();
    int height = puzzle.getHeight();

    gridPane.getChildren().clear();
    gridPane.getColumnConstraints().clear();
    gridPane.getRowConstraints().clear();

    for (int col = 0; col < width; col++) {
      ColumnConstraints colConstraints = new ColumnConstraints();
      colConstraints.setPercentWidth(100.0 / width);
      gridPane.getColumnConstraints().add(colConstraints);
    }

    for (int row = 0; row < height; row++) {
      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setPercentHeight(100.0 / height);
      gridPane.getRowConstraints().add(rowConstraints);
    }

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Button cellButton = new Button();
        cellButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        CellType cellType = puzzle.getCellType(row, col);

        if (cellType == CellType.WALL) {
          cellButton.getStyleClass().add("wall-cell");
          cellButton.setDisable(true);
        } else if (cellType == CellType.CLUE) {
          int clue = puzzle.getClue(row, col);
          cellButton.getStyleClass().add("clue-cell");
          cellButton.setText(String.valueOf(clue));
          cellButton.setDisable(true);
        } else if (cellType == CellType.CORRIDOR) {
          cellButton.getStyleClass().add("corridor-cell");

          // Add 'lamp' class if a lamp is placed
          if (model.isLamp(row, col)) {
            cellButton.getStyleClass().add("lamp");
          }
          // Add 'lit' class if the cell is lit
          else if (model.isLit(row, col)) {
            cellButton.getStyleClass().add("lit");
          }
        }

        final int finalRow = row;
        final int finalCol = col;

        cellButton.setOnAction(
            e -> {
              if (cellType == CellType.CORRIDOR) {
                if (model.isLamp(finalRow, finalCol)) {
                  model.removeLamp(finalRow, finalCol);
                } else {
                  model.addLamp(finalRow, finalCol);
                }
              }
            });

        gridPane.add(cellButton, col, row);
      }
    }

    return gridPane;
  }
}
