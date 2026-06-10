package com.comp301.a09akari.view;

import static com.comp301.a09akari.SamplePuzzles.PUZZLE_01;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_02;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_03;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_04;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_05;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.PuzzleImpl;
import com.comp301.a09akari.model.PuzzleLibraryImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // TODO: Create your Model, View, and Controller instances and launch your GUI
    // Fill Puzzle Library with samples
    PuzzleImpl sample1 = new PuzzleImpl(PUZZLE_01);
    PuzzleImpl sample2 = new PuzzleImpl(PUZZLE_02);
    PuzzleImpl sample3 = new PuzzleImpl(PUZZLE_03);
    PuzzleImpl sample4 = new PuzzleImpl(PUZZLE_04);
    PuzzleImpl sample5 = new PuzzleImpl(PUZZLE_05);
    PuzzleLibraryImpl library = new PuzzleLibraryImpl();
    library.addPuzzle(sample1);
    library.addPuzzle(sample2);
    library.addPuzzle(sample3);
    library.addPuzzle(sample4);
    library.addPuzzle(sample5);

    // Create model
    ModelImpl model = new ModelImpl(library);
    // Create controller
    ControllerImpl controller = new ControllerImpl(model);

    // FXComponents
    PuzzleView puzzleView = new PuzzleView(model);
    ControlView controlView = new ControlView(controller);
    MessageView messageView = new MessageView(model);


    BorderPane root = new BorderPane();


    root.setCenter(puzzleView.render());


    root.setBottom(controlView.render());


    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add("main.css");
    stage.setTitle("Akari Puzzle");
    stage.setScene(scene);
    stage.show();
  }
}
