package edu.ntnu.stud;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import edu.ntnu.stud.userInterface.UserInterface;
import java.time.LocalTime;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {

  // TODO: Fill in the main method and any other methods you need.

  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}
