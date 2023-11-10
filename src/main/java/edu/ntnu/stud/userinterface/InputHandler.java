package edu.ntnu.stud.userinterface;

import java.util.Scanner;

public class InputHandler {
  private final StringManager stringManager;
  private final Scanner scanner = new Scanner(System.in);

  public InputHandler(StringManager stringManager) {
    this.stringManager = stringManager;
  }

  public String getStringInput() {
    return scanner.nextLine();
  }

}

