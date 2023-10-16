package edu.ntnu.stud;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {
  // TODO: Fill in the main method and any other methods you need.

  public static void main(String[] args) {
    TrainDeparture train1 = new TrainDeparture(1, 1, "3", "Moholt", LocalTime.of(5, 40));

  }
}
