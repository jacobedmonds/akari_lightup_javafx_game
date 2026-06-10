package com.comp301.a09akari.view;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

public class MessageView implements FXComponent, ModelObserver {

  private final ModelImpl model;

  public MessageView(ModelImpl model) {
    this.model = model;
    this.model.addObserver(this);
  }

  @Override
  public void update(Model model) {

    if (model.isSolved()) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Puzzle Solved");
      alert.setHeaderText(null);
      alert.setContentText("Congratulations! You solved the puzzle.");
      alert.showAndWait();
    }
  }

  @Override
  public Parent render() {

    return new javafx.scene.layout.StackPane();
  }
}
