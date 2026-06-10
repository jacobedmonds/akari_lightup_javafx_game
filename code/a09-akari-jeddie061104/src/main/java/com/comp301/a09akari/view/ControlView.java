package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ControlView implements FXComponent {

  private final ControllerImpl controller;
  private final Label puzzleIndexLabel;

  public ControlView(ControllerImpl controller) {
    this.controller = controller;
    this.puzzleIndexLabel = new Label();
    updatePuzzleIndex();
  }

  @Override
  public HBox render() {
    HBox controls = new HBox(10);
    controls.getStyleClass().add("hbox-controls");

    Button nextButton = new Button("Next");
    nextButton.getStyleClass().add("button");
    nextButton.setOnAction(
        e -> {
          controller.clickNextPuzzle();
          updatePuzzleIndex();
          checkPuzzleSolved();
        });

    Button prevButton = new Button("Previous");
    prevButton.getStyleClass().add("button");
    prevButton.setOnAction(
        e -> {
          controller.clickPrevPuzzle();
          updatePuzzleIndex();
          checkPuzzleSolved();
        });

    Button randButton = new Button("Random");
    randButton.getStyleClass().add("button");
    randButton.setOnAction(
        e -> {
          controller.clickRandPuzzle();
          updatePuzzleIndex();
          checkPuzzleSolved();
        });

    Button restartButton = new Button("Reset");
    restartButton.getStyleClass().addAll("button", "reset");
    restartButton.setOnAction(
        e -> {
          controller.clickResetPuzzle();
          checkPuzzleSolved();
        });

    puzzleIndexLabel.getStyleClass().add("label");
    updatePuzzleIndex();

    controls
        .getChildren()
        .addAll(prevButton, nextButton, randButton, restartButton, puzzleIndexLabel);
    return controls;
  }

  private void updatePuzzleIndex() {
    int currentPuzzle = controller.getModel().getActivePuzzleIndex() + 1;
    int totalPuzzles = controller.getModel().getPuzzleLibrarySize();
    puzzleIndexLabel.setText("Puzzle " + currentPuzzle + " of " + totalPuzzles);
  }

  private void checkPuzzleSolved() {
    if (controller.getModel().isSolved()) {
      showSolvedMessage();
    }
  }

  private void showSolvedMessage() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Puzzle Solved!");
    alert.setHeaderText("Congratulations!");
    alert.setContentText("You have completed the puzzle.");
    alert.showAndWait();
  }
}
