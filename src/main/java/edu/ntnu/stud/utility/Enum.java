package edu.ntnu.stud.utility;

public enum Enum {
  TRAIN_REMOVED_BY_DELAY(1),
  DELAY_CHANGED_SUCCESSFULLY(2),
  TRAIN_NUMBER_NOT_IN_USE(3);

  private final int value;

  Enum(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
