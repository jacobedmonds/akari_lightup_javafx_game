package com.comp301.a09akari.view;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class view implements FXComponent {
  @Override
  public Parent render() {
    Pane vbox = new VBox();

    return vbox;
  }
}
