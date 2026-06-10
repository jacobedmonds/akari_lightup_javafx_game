package com.comp301.a09akari.model;

public class ObserverImpl implements ModelObserver {
  @Override
  public void update(Model model) {
    System.out.println("The model has been updated!");
  }
}
