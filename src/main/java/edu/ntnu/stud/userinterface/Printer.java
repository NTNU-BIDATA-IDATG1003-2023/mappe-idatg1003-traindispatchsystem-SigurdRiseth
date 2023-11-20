package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Iterator;

/**
 * Class for managing all the methods creating strings used in the UI.
 *
 * @version 0.0.1
 * @Author Sigurd Riseth
 */
public class Printer {

  /**
   * Prints all the options the user can choose from.
   */
  public void printOptions() {
    System.out.println(String.join("\n",
        "-------------------------------------------",
        "1: Print all upcoming departures",
        "2: Print all upcoming departures to a given destination",
        "3: Print the next departure to a given destination",
        "4: Add a new train departure",
        "5: Set delay for a train departure",
        "6: Set track for a train departure",
        "7: Remove track for a train departure",
        "8: Get train by train number",
        "9: Remove train by train number",
        "10: Set the clock",
        "0: Exit",
        "Please enter a number between 0 and 10:")
    );
  }

  /**
   * Prints a String.
   *
   * @param string The string to be printed
   */

  public void print(String string) {
    System.out.println(string);
  }


  /**
   * Prints a String of all departures yet to depart.
   */
  public void printAllDepartures(Iterator<TrainDeparture> iterator) {
    StringBuilder result = new StringBuilder();
    result.append("Here is a list of all the trains that are yet to depart:\n");
    result.append("\033[1m" + String.format("%-15s %-10s %-20s %-20s %-15s %-15s %n",
        "Train number", "Line", "Destination", "Departure time", "Track", "Delay") + "\033[0m");
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      String formattedLine = String.format("%-15s %-10s %-20s %-20s %-15s %-15s",
          trainDeparture.getTrainNumber(),
          trainDeparture.getLine(),
          trainDeparture.getDestination(),
          trainDeparture.getDepartureTime(),
          (trainDeparture.getTrack() == -1) ? "-" : String.valueOf(trainDeparture.getTrack()),
          (trainDeparture.getDelay() == LocalTime.of(0, 0)) ? "-" : trainDeparture.getDelay());
      result.append(formattedLine).append("\n");
    }
    System.out.println(result);
  }

  /**
   * Prints all departures to a given destination. The destination can be given in any
   * capitalisation. If no departures are found, a message is printed.
   *
   * @param destination The destination to search for
   */
  public void printAllDeparturesToDestination(String destination,
      Iterator<TrainDeparture> iterator) {
    boolean foundDeparture = false;
    System.out.println("Here is a list of all the trains that are yet to depart to " + destination
        + ":");
    System.out.println(("\033[1m" + String.format("%-15s %-10s %-20s %-20s %-15s %-15s",
        "Train number", "Line", "Destination", "Departure time", "Track", "Delay") + "\033[0m"));
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      if (trainDeparture.getDestination().equals(destination)) {
        String formattedLine = String.format("%-15s %-10s %-20s %-20s %-15s %-15s",
            trainDeparture.getTrainNumber(),
            trainDeparture.getLine(),
            trainDeparture.getDestination(),
            trainDeparture.getDepartureTime(),
            (trainDeparture.getTrack() == -1) ? "-" : String.valueOf(trainDeparture.getTrack()),
            (trainDeparture.getDelay() == LocalTime.of(0, 0)) ? "-" : trainDeparture.getDelay());
        System.out.println(formattedLine);
        foundDeparture = true;
      }
    }
    if (!foundDeparture) {
      System.out.println("No trains to " + destination + " found.");
    }
  }

  /**
   * Prints the next departure to a given destination. If no departures are found, a message is
   * printed.
   *
   * @param trainDeparture The destination to search for
   */
  public void printNextDepartureToDestination(TrainDeparture trainDeparture) {
    StringBuilder result = new StringBuilder();
    result.append("The next train to " + trainDeparture.getDestination());
    result.append(" departs at " + trainDeparture.getDepartureTime());
    if (trainDeparture.getTrack() == -1) {
      result.append(" from track (not yet assigned)");
    } else {
      result.append(" from track " + trainDeparture.getTrack());
    }
    System.out.println(result);

  }

  /**
   * Asks the user to enter a destination.
   */
  public void printDestinationAsk() {
    System.out.println("Please enter a destination: ");
  }

  /**
   * Asks the user to enter a train number.
   */
  public void printTrainNumberAsk() {
    System.out.println("Please enter a train number: ");
  }

  /**
   * Asks the user to enter a line.
   */
  public void printLineAsk() {
    System.out.println("Please enter a line: ");
  }

  /**
   * Prints a message indicating that the train number is invalid.
   */
  public void printTrainNumberInvalid() {
    System.out.println(
        "The train number must be a whole number above 0. Please try again.");
  }

  /**
   * Prints a message indicating that the train number is already in use.
   */
  public void printTrainNumberInUse() {
    System.out.println("The train number is already in use. Please try again.");
  }

  /**
   * Asks the user to enter a time in format hh:mm.
   */
  public void printTimeAsk() {
    System.out.println("Please enter time in format (hh:mm): ");
  }

  /**
   * Asks the user to input a track or type none.
   */
  public void printTrackAsk() {
    System.out.println("Please enter a track (type \"none\" if you do not wish to assign one):");
  }

  /**
   * Prints a message indicating that the time is invalid.
   */
  public void printTimeInvalid() {
    System.out.println("Please try again and enter a valid time (in format hh:mm).");
  }

  /**
   * Tells the user that the train does not exist.
   */
  public void printTrainNumberNotInUse() {
    System.out.println("The train number does not exist. Please try again.");
  }

  /**
   * Prints information about a train departure.
   *
   * @param train The train departure to be printed
   */
  public void printTrainDeparture(TrainDeparture train) {
    System.out.println("Train number: " + train.getTrainNumber());
    System.out.println("Line: " + train.getLine());
    System.out.println("Destination: " + train.getDestination());
    System.out.println("Departure time: " + train.getDepartureTime());
    System.out.print("Track: ");
    System.out.println(
        (train.getTrack() == -1) ? "Not yet assigned" : String.valueOf(train.getTrack()));
  }
}
