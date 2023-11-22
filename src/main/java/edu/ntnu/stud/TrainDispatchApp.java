package edu.ntnu.stud;

import edu.ntnu.stud.userinterface.UserInterface;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {

  /**
   * Main method for the train dispatch application.
   * <p>Creates a new instance of the UserInterface class and calls the init and start methods.</p>
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}
