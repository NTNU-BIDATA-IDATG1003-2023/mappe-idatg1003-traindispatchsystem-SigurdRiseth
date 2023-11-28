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


  private final String TABLEFORMAT = "%-15s %-10s %-20s %-20s %-15s %-15s %n";

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
   * Prints a String of all departures yet to depart.
   * <p>Uses a iterator of train departures as parameter.</p>
   */
  public void printAllDepartures(Iterator<TrainDeparture> iterator) {
    StringBuilder result = new StringBuilder();
    result.append("Here is a list of all the trains that are yet to depart:\n");
    result.append("\033[1m" + String.format(TABLEFORMAT,
        "Train number", "Line", "Destination", "Departure time", "Track", "Delay") + "\033[0m");
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      result.append(formatTrainToTable(trainDeparture));
    }
    System.out.println(result);
  }

  /**
   * Formats a train departure to the table format.
   * <p>The track will not be shown if it equals -1 and the delay will not be shown if it is 00:00.</p>
   *
   * @param trainDeparture The train departure to be formatted
   * @return The formatted train departure
   */
  private String formatTrainToTable(TrainDeparture trainDeparture) {
    return String.format(TABLEFORMAT,
        trainDeparture.getTrainNumber(),
        trainDeparture.getLine(),
        trainDeparture.getDestination(),
        trainDeparture.getDepartureTime(),
        (trainDeparture.getTrack() == -1) ? "-" : String.valueOf(trainDeparture.getTrack()),
        (trainDeparture.getDelay() == LocalTime.of(0, 0)) ? "-" : trainDeparture.getDelay());
  }

  /**
   * Prints all departures to a given destination. If no departures are found, a message is printed.
   *
   * @param destination The destination to search for
   */
  public void printAllDeparturesToDestination(String destination,
      Iterator<TrainDeparture> iterator) {
    boolean foundDeparture = false;
    StringBuilder result = new StringBuilder();
    result.append("Here is a list of all the trains that are yet to depart to " + destination
        + ": \n");
    result.append("\033[1m" + String.format(TABLEFORMAT,
        "Train number", "Line", "Destination", "Departure time", "Track", "Delay") + "\033[0m");
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
    System.err.println("The train number must be a whole number above 0. Please try again.");
  }

  /**
   * Prints a message indicating that the train number is already in use.
   */
  public void printTrainNumberInUse() {
    System.err.println("The train number is already in use. Please try again.");
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
    System.err.println("Please try again and enter a valid time (in format hh:mm).");
  }

  /**
   * Tells the user that the train does not exist.
   */
  public void printTrainNumberNotInUse() {
    System.err.println("The train number does not exist. Please try again.");
  }

  /**
   * Prints information about a train departure.
   * <p>The track will not be showed if it equals -1, and the delay will not be shown if it is 00:00.</p>
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
    System.out.print("Delay: ");
    System.out.println((train.getDelay() == LocalTime.of(0, 0)) ? "No delay" : train.getDelay());
  }

  /**
   * Prints a message saying the menu choice is out of bounds.
   */
  public void printInvalidChoice() {
    System.err.println("Please enter a valid number. The number should be between 0 and 10");
  }

  /**
   * Prints a message saying that no train departure has been created yet and only choice 4, 10 and 0 is allowed.
   */
  public void printNoTrains() {
    System.err.println("No TrainDepartures created yet. Only option 4, 10, or 0 is allowed.");
  }

  /**
   * Prints a goodbye message saying the application has been terminated.
   */
  public void printCloseApp() {
    System.out.println("Thank you for using the train dispatch app!");
    System.out.println("The application has now been terminated.");
  }

  /**
   * Prints the clock.
   *
   * @param clock The clock at the station
   */
  public void printClock(LocalTime clock) {
    System.out.println("The clock is now " + clock);
  }

  /**
   * Prints a message that the train has been removed.
   */
  public void printTrainRemoved() {
    System.out.println("Train has been removed.");
  }

  /**
   * Prints a message that the train has been removed as it was delayed over midnight.
   */
  public void printTrainRemovedByDelay() {
    System.out.println("Train has been removed as it was delayed over midnight.");
  }

  /**
   * Prints a message that no train to the given destination was found.
   *
   * @param destination The destination that was searched for
   */
  public void printNoTrainFound(String destination) {
    System.err.println("No train to " + destination + " was found.");
  }

  /**
   * Prints a message that the train has been added.
   */
  public void printTrainAdded() {
    System.out.println("Train departure has been added!");
  }

  /**
   * Prints an error message saying the input time can not be before the station clock.
   *
   * @param clock The clock at the station
   */
  public void printBeforeClock(LocalTime clock) {
    System.err.println("You can not input a time before "
        + clock + ". Please try again.");
  }

  /**
   * Prints a welcome message when the application starts.
   */
  public void printWelcomeMessage() {
    System.out.println("--------------------------------------------------");
    System.out.println("Welcome to the train dispatch app!");
  }

  /**
   * Prints a confirmation message that the delay has been changed.
   */
  public void printDelayChanged() {
    System.out.println("Delay changed.");
  }

  /**
   * Prints a confirmation message that the track has been removed.
   */
  public void printTrackRemoved() {
    System.out.println("Track removed.");
  }

  /**
   * Prints a confirmation message that the track has been changed.
   *
   * @param track The new track
   */
  public void printTrackChanged(String track) {
    System.out.println("Track changed to " + track);
  }

  /**
   * Prints a message that the clock has been changed.
   *
   * @param time The new time
   */
  public void printClockChanged(LocalTime time) {
    System.out.println("Clock changed to " + time);
  }

  /**
   * Prints a message that the clock was not changed as the user tried to set it backwards.
   */
  public void printClockNotChanged() {
    System.err.println("Clock was not changed. Can not set clock backwards. Please try again.");
  }
}
