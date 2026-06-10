package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;

public class ControllerImpl implements ClassicMvcController {

  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    if (model.getPuzzleLibrarySize() == 1) {
      model.resetPuzzle();
    } else if (model.getActivePuzzleIndex() + 1 == model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(0);
    } else { // Is getting ran :)
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int librarySize = model.getPuzzleLibrarySize();
    int puzzleIndex = model.getActivePuzzleIndex();
    if (librarySize == 1) {
      model.resetPuzzle();
      return;
    }
    if (puzzleIndex == 0) {
      model.setActivePuzzleIndex(librarySize - 1);
    } else {
      model.setActivePuzzleIndex(puzzleIndex - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int librarySize = model.getPuzzleLibrarySize();
    if (librarySize == 1) {
      model.resetPuzzle();
      return;
    }
    int upper = librarySize - 1;
    int lower = 0;
    int randIdx = (int) (Math.random() * (upper - lower + 1)) + lower;
    model.setActivePuzzleIndex(randIdx);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    model.addLamp(r, c);
  }

  public Model getModel() {
    return this.model;
  }
}
