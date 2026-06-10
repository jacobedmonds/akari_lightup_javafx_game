package com.comp301.a09akari.model;

import static com.comp301.a09akari.model.CellType.*;

public class PuzzleImpl implements Puzzle {

  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r < 0 || r > this.getHeight() - 1) {
      throw new IndexOutOfBoundsException("r is out of bounds.");
    }
    if (c < 0 || c > this.getWidth() - 1) {
      throw new IndexOutOfBoundsException("c is out of bounds.");
    }
    int cell = board[r][c];
    if (cell >= 0 && cell <= 4) {
      return CLUE;
    } else if (cell == 5) {
      return WALL;
    } else if (cell == 6) {
      return CORRIDOR;
    } else {
      return null;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r < 0 || r > this.getHeight() - 1) {
      throw new IndexOutOfBoundsException("r is out of bounds.");
    }
    if (c < 0 || c > this.getWidth() - 1) {
      throw new IndexOutOfBoundsException("c is out of bounds.");
    }
    if (this.getCellType(r, c) != CLUE) {
      throw new IllegalArgumentException("Wrong cell type.");
    }
    return board[r][c];
  }
}
