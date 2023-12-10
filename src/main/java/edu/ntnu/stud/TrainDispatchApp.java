package edu.ntnu.stud;

import edu.ntnu.stud.userinterface.UserInterface;

public class TrainDispatchApp {

  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}
