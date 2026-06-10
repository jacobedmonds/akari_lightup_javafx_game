package com.comp301.a09akari.model;

import static com.comp301.a09akari.model.CellType.CORRIDOR;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.util.Pair;

public class ModelImpl implements Model {

  private final PuzzleLibrary library;
  private Puzzle activePuzzle;
  private final Set<Pair<Integer, Integer>> lamps;
  private final List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new NullPointerException("Library is null.");
    }
    this.library = library;
    this.activePuzzle = library.getPuzzle(0);
    this.lamps = new HashSet<>();
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r < 0 || r > activePuzzle.getHeight() - 1) {
      throw new IndexOutOfBoundsException("r is out of bounds.");
    }
    if (c < 0 || c > activePuzzle.getWidth() - 1) {
      throw new IndexOutOfBoundsException("c is out of bounds.");
    }
    if (activePuzzle.getCellType(r, c) != CORRIDOR) {
      throw new IllegalArgumentException("Cell type is not a corridor.");
    }
    if (lamps.contains(new Pair<>(r, c))) {
      return;
    }
    lamps.add(new Pair<>(r, c));
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r < 0 || r > activePuzzle.getHeight() - 1) {
      throw new IndexOutOfBoundsException("r is out of bounds.");
    }
    if (c < 0 || c > activePuzzle.getWidth() - 1) {
      throw new IndexOutOfBoundsException("c is out of bounds.");
    }
    if (activePuzzle.getCellType(r, c) != CORRIDOR) {
      throw new IllegalArgumentException("Cell type is not a corridor.");
    }
    lamps.remove(new Pair<>(r, c));
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r < 0 || r >= activePuzzle.getHeight()) {
      throw new IndexOutOfBoundsException("r is out of bounds.");
    }
    if (c < 0 || c >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("c is out of bounds.");
    }
    if (activePuzzle.getCellType(r, c) != CORRIDOR) {
      throw new IllegalArgumentException("Cell type is not a corridor.");
    }

    // Check if lamp is present at the cell
    if (lamps.contains(new Pair<>(r, c))) {
      return true;
    }

    // Check for light in all four directions
    int[][] directions = {
      {-1, 0}, // Up
      {1, 0}, // Down
      {0, -1}, // Left
      {0, 1} // Right
    };

    for (int[] dir : directions) {
      int row = r;
      int col = c;
      while (true) {
        row += dir[0];
        col += dir[1];
        if (row < 0
            || row >= activePuzzle.getHeight()
            || col < 0
            || col >= activePuzzle.getWidth()) {
          break; // Out of bounds
        }
        CellType type = activePuzzle.getCellType(row, col);
        if (type == CellType.WALL || type == CellType.CLUE) {
          break; // Obstruction
        }
        if (lamps.contains(new Pair<>(row, col))) {
          return true; // Lamp found
        }
      }
    }

    return false; // Not lit
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r < 0 || r > activePuzzle.getHeight() - 1) {
      throw new IndexOutOfBoundsException("r is out of bounds.");
    }
    if (c < 0 || c > activePuzzle.getWidth() - 1) {
      throw new IndexOutOfBoundsException("c is out of bounds.");
    }
    if (activePuzzle.getCellType(r, c) != CORRIDOR) {
      throw new IllegalArgumentException("Cell type is not a corridor.");
    }
    return lamps.contains(new Pair<>(r, c));
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r < 0 || r > activePuzzle.getHeight() - 1) {
      throw new IndexOutOfBoundsException("r is out of bounds.");
    }
    if (c < 0 || c > activePuzzle.getWidth() - 1) {
      throw new IndexOutOfBoundsException("c is out of bounds.");
    }
    if (activePuzzle.getCellType(r, c) != CORRIDOR || !lamps.contains(new Pair<>(r, c))) {
      throw new IllegalArgumentException("Cell does not contain a lamp.");
    }

    for (int i = r - 1; i >= 0; i--) {
      if (activePuzzle.getCellType(i, c) == CellType.WALL
          || activePuzzle.getCellType(i, c) == CellType.CLUE) {
        break;
      }
      if (lamps.contains(new Pair<>(i, c))) {
        return true;
      }
    }

    for (int i = r + 1; i < activePuzzle.getHeight(); i++) {
      if (activePuzzle.getCellType(i, c) == CellType.WALL
          || activePuzzle.getCellType(i, c) == CellType.CLUE) {
        break;
      }
      if (lamps.contains(new Pair<>(i, c))) {
        return true;
      }
    }

    for (int j = c - 1; j >= 0; j--) {
      if (activePuzzle.getCellType(r, j) == CellType.WALL
          || activePuzzle.getCellType(r, j) == CellType.CLUE) {
        break;
      }
      if (lamps.contains(new Pair<>(r, j))) {
        return true;
      }
    }

    for (int j = c + 1; j < activePuzzle.getWidth(); j++) {
      if (activePuzzle.getCellType(r, j) == CellType.WALL
          || activePuzzle.getCellType(r, j) == CellType.CLUE) {
        break;
      }
      if (lamps.contains(new Pair<>(r, j))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return activePuzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    for (int i = 0; i < getPuzzleLibrarySize(); i++) {
      if (library.getPuzzle(i) == activePuzzle) {
        return i;
      }
    }
    throw new IllegalArgumentException("Index was not found.");
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= getPuzzleLibrarySize()) {
      throw new IndexOutOfBoundsException("Index is out of bounds.");
    }
    activePuzzle = library.getPuzzle(index); // Update the active puzzle
    resetPuzzle();
    System.out.println(index);
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    activePuzzle = library.getPuzzle(getActivePuzzleIndex());
    lamps.clear();
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    int height = activePuzzle.getHeight();
    int width = activePuzzle.getWidth();
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        CellType cellType = activePuzzle.getCellType(r, c);
        if (cellType == CellType.CLUE && !isClueSatisfied(r, c)) {
          System.out.println("Unmet clue at: (" + r + ", " + c + ")");
          return false;
        }
        if (cellType == CellType.CORRIDOR) {
          if (!isLit(r, c)) {
            System.out.println("Unlit corridor at: (" + r + ", " + c + ")");
            return false;
          }
        }
        if (lamps.contains(new Pair<>(r, c))) {
          if (isLampIllegal(r, c)) {
            System.out.println("Lamp conflict at: (" + r + ", " + c + ")");
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r < 0 || r >= activePuzzle.getHeight()) {
      throw new IndexOutOfBoundsException("r is out of bounds.");
    }
    if (c < 0 || c >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("c is out of bounds.");
    }

    if (activePuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell is not of type CLUE.");
    }

    int clueValue = activePuzzle.getClue(r, c);

    int[][] directions = {
      {-1, 0}, // Up
      {1, 0}, // Down
      {0, -1}, // Left
      {0, 1} // Right
    };

    int lampCount = 0;
    for (int[] dir : directions) {
      int newRow = r + dir[0];
      int newCol = c + dir[1];
      if (newRow >= 0
          && newRow < activePuzzle.getHeight()
          && newCol >= 0
          && newCol < activePuzzle.getWidth()
          && lamps.contains(new Pair<>(newRow, newCol))) {
        lampCount++;
      }
    }

    return lampCount == clueValue;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new NullPointerException("Observer is null.");
    }
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver obs : observers) {
      obs.update(this);
    }
  }
}
