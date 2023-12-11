package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Iterator;

/**
 * The Printer class manages methods responsible for printing information to the terminal in the
 * Train Dispatch System.
 *
 * <p>
 * Methods in this class handle the presentation of options, train departures, error messages, and
 * user prompts. The class employs formatting for displaying train departure details in a
 * table format, ensuring readability and user-friendly output.
 * </p>
 *
 * @author Sigurd Riseth
 * @version 1.0.0
 */
public class Printer {


  private final String TABLE_FORMAT = "%-15s %-10s %-20s %-20s %-15s %-15s %n";

  /**
   * Prints all the options the user can choose from.
   */
  void printOptions() {
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
   * Prints a String of all departures yet to depart.
   *
   * <p>
   *   Uses a iterator of train departures as parameter.
   *   Prints all departures in the table format.
   * </p>
   *
   * @param iterator The iterator of train departures
   */
  void printAllDepartures(Iterator<TrainDeparture> iterator) {
    StringBuilder result = new StringBuilder();
    result.append("Here is a list of all the trains that are yet to depart:\n");
    result.append("\033[1m").append(String.format(TABLE_FORMAT,
            "Departure time", "Line", "Train Number", "Destination", "Delay", "Track"))
        .append("\033[0m");
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      result.append(formatTrainToTable(trainDeparture));
    }
    System.out.println(result);
  }

  /**
   * Formats a train departure to the table format.
   *
   * <p>
   * The track will not be shown if it equals -1 and the delay will not be shown if it is 00:00.
   * </p>
   *
   * @param trainDeparture The train departure to be formatted
   * @return The formatted train departure
   */
  private String formatTrainToTable(TrainDeparture trainDeparture) {
    return String.format(TABLE_FORMAT,
        trainDeparture.getDepartureTime(),
        trainDeparture.getLine(),
        trainDeparture.getTrainNumber(),
        trainDeparture.getDestination(),
        (trainDeparture.getDelay() == LocalTime.of(0, 0)) ? "-" : trainDeparture.getDelay(),
        (trainDeparture.getTrack() == -1) ? "-" : String.valueOf(trainDeparture.getTrack()));
  }

  /**
   * Prints all departures to a given destination.
   *
   * <p>
   *   Uses a iterator of train departures as parameter.
   *   Prints all departures to the given destination in the table format.
   *   If no departures are found, a message is printed instead.
   * </p>
   *
   * @param destination The destination to search for
   * @param iterator The iterator of train departures
   */
  void printAllDeparturesToDestination(String destination,
      Iterator<TrainDeparture> iterator) {
    boolean foundDeparture = false;
    StringBuilder result = new StringBuilder();
    result.append("Here is a list of all the trains that are yet to depart to ").append(destination)
        .append(": \n");
    result.append("\033[1m").append(String.format(TABLE_FORMAT,
            "Departure time", "Line", "Train Number", "Destination", "Delay", "Track"))
        .append("\033[0m");
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      if (trainDeparture.getDestination().equals(destination)) {
        result.append(formatTrainToTable(trainDeparture));
        foundDeparture = true;
      }
    }
    if (!foundDeparture) {
      printNoTrainFound(destination);
    } else {
      System.out.println(result);
    }
  }

  /**
   * Asks the user to enter a destination.
   */
  void printDestinationAsk() {
    System.out.println("Please enter a destination: ");
  }

  /**
   * Asks the user to enter a train number.
   */
  void printTrainNumberAsk() {
    System.out.println("Please enter a train number: ");
  }

  /**
   * Asks the user to enter a line.
   */
  void printLineAsk() {
    System.out.println("Please enter a line: ");
  }

  /**
   * Prints a message indicating that the train number is invalid.
   */
  void printTrainNumberInvalid() { // flush ut error message
    System.err.println("The train number must be a whole number above 0. Please try again.");
  }

  /**
   * Prints a message indicating that the train number is already in use.
   */
  void printTrainNumberInUse() {
    System.err.println("The train number is already in use. Please try again.");
  }

  /**
   * Asks the user to enter a time in format hh:mm.
   */
  void printTimeAsk() {
    System.out.println("Please enter time in format (hh:mm): ");
  }

  /**
   * Asks the user to input a track or type none.
   */
  void printTrackAsk() {
    System.out.println("Please enter a track (type \"none\" if you do not wish to assign one):");
  }

  /**
   * Prints a message indicating that the time is invalid.
   */
  void printTimeInvalid() {
    System.err.println("Please try again and enter a valid time (in format hh:mm).");
  }

  /**
   * Tells the user that the train does not exist.
   */
  void printTrainNumberNotInUse() {
    System.err.println("The train number does not exist. Please try again.");
  }

  /**
   * Prints information about a single train departure.
   *
   * <p>
   * The track will not be showed if it equals -1, and the delay will not be shown if it is 00:00.
   * </p>
   *
   * @param train The train departure to be printed
   */
  void printTrainDeparture(TrainDeparture train) {
    System.out.println("Train number: " + train.getTrainNumber());
    System.out.println("Line: " + train.getLine());
    System.out.println("Destination: " + train.getDestination());
    System.out.println("Departure time: " + train.getDepartureTime());
    System.out.print("Track: ");
    System.out.println(
        (train.getTrack() == -1) ? "Not yet assigned" : String.valueOf(train.getTrack()));
    System.out.print("Delay: ");
    System.out.println((train.getDelay() == LocalTime.of(0, 0)) ? "No delay" : train.getDelay());
  }

  /**
   * Prints a message saying the menu choice is out of bounds.
   */
  void printInvalidChoice() {
    System.err.println("Please enter a valid number. The number should be between 0 and 10");
  }

  /**
   * Prints a message saying that no train departure has been created yet and only choice 4, 10 and
   * 0 is allowed.
   */
  void printNoTrains() {
    System.err.println("No TrainDepartures created yet. Only option 4, 10, or 0 is allowed.");
  }

  /**
   * Prints a goodbye message saying the application has been terminated.
   */
  void printCloseApp() {
    System.out.println("Thank you for using the train dispatch app!");
    System.out.println("The application has now been terminated.");
  }

  /**
   * Prints the clock.
   *
   * @param clock The clock at the station
   */
  void printClock(LocalTime clock) {
    System.out.println("The clock is now " + clock);
  }

  /**
   * Prints a message that the train has been removed.
   */
  void printTrainRemoved() {
    System.out.println("Train has been removed.");
  }

  /**
   * Prints a message that the train has been removed as it was delayed over midnight.
   */
  void printTrainRemovedByDelay() {
    System.out.println("Train has been removed as it was delayed over midnight.");
  }

  /**
   * Prints a message that no train to the given destination was found.
   *
   * @param destination The destination that was searched for
   */
  void printNoTrainFound(String destination) {
    System.err.println("No train to " + destination + " was found.");
  }

  /**
   * Prints a message that the train has been added.
   */
  void printTrainAdded() {
    System.out.println("Train departure has been added!");
  }

  /**
   * Prints an error message saying the input time can not be before the station clock.
   *
   * @param clock The clock at the station
   */
  void printBeforeClock(LocalTime clock) {
    System.err.println("You can not input a time before "
        + clock + ". Please try again.");
  }

  /**
   * Prints a welcome message when the application starts.
   */
  void printWelcomeMessage() {
    System.out.println("--------------------------------------------------");
    System.out.println("Welcome to the train dispatch app!");
  }

  /**
   * Prints a confirmation message that the delay has been changed.
   */
  void printDelayChanged() {
    System.out.println("Delay changed.");
  }

  /**
   * Prints a confirmation message that the track has been removed.
   */
  void printTrackRemoved() {
    System.out.println("Track removed.");
  }

  /**
   * Prints a confirmation message that the track has been changed.
   *
   * @param track The new track
   */
  void printTrackChanged(String track) {
    System.out.println("Track changed to " + track);
  }

  /**
   * Prints a message that the clock has been changed.
   *
   * @param time The new time
   */
  void printClockChanged(LocalTime time) {
    System.out.println("Clock changed to " + time);
  }

  /**
   * Prints a message that the clock was not changed as the user tried to set it backwards.
   */
  void printClockNotChanged() {
    System.err.println("Clock was not changed. Can not set clock backwards. Please try again.");
  }
}
