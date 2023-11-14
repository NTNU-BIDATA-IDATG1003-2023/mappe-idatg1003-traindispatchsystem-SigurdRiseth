package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Class for handling user input.
 * <p>Handles all user input and calls the needed methods in the StringManager class.</p>
 *
 * @Author Sigurd Riseth
 * @version 0.0.1
 */
public class InputHandler {

  private final StringManager stringManager;
  private final Station station;
  private final Scanner scanner = new Scanner(System.in);

  /**
   * Constructor for the InputHandler class.
   * <p>Initializes the stringManager field.</p>
   *
   * @param stringManager The StringManager object
   */
  public InputHandler(StringManager stringManager, Station station) {
    this.stringManager = stringManager;
    this.station = station;
  }

  /**
   * Gets the user input as a String.
   *
   * @return The user input as a String
   */
  public String getStringInput() {
    return scanner.nextLine();
  }

  /**
   * Asks the user for a train number. Will keep asking until the user inputs an unused and valid
   * train number.
   *
   * @return The train number
   */
  public int getTrainNumberUnused() {
    int trainNumber = 0;

    do {
      stringManager.printTrainNumberAsk();
      try {
        trainNumber = scanner.nextInt();
        scanner.nextLine();

        if (trainNumber < 1) {
          stringManager.printTrainNumberInvalid();
        } else if (station.trainExists(trainNumber)) {
          stringManager.printTrainNumberInUse();
          trainNumber = -1;
        }
      } catch (Exception e) {
        stringManager.printTrainNumberInvalid();
        scanner.nextLine();
      }
    } while (trainNumber < 1);
    return trainNumber;
  }

  /**
   * Runs StringManagers method printLineAsk() and returns the user input.
   *
   * @return The line
   */
  public String getLine() {
    stringManager.printLineAsk();
    return scanner.nextLine();
  }

  /**
   * Runs StringManagers method printDestinationAsk() and stores the user input. Capitalizes the
   * first letter and lowercase the rest before returning.
   *
   * @return The destination
   */
  public String getDestination() {
    stringManager.printDestinationAsk();
    String destination = scanner.nextLine();
    return destination.substring(0, 1).toUpperCase()
        + destination.substring(1).toLowerCase();
  }

  /**
   * Runs StringManagers method printTimeAsk() and stores the user input. Will keep asking until the
   * user inputs a valid time.
   *
   * @return The departure time
   */
  public LocalTime getLocalTimeFromString() {
    LocalTime departureTime = null;
    while (departureTime == null) {
      stringManager.printTimeAsk();
      String inputDepartureTime = scanner.nextLine();
      if (departureTimeValid(inputDepartureTime)) {
        departureTime = LocalTime.parse(inputDepartureTime);
        return departureTime;
      } else {
        stringManager.printTimeInvalid();
      }
    }
    return departureTime;
  }

  /**
   * Runs StringManagers method getLocalTimeFromStringAfterClock() and stores the user input. Will
   * keep asking until the user inputs a valid time after the current time.
   *
   * @return The departure time
   */
  public LocalTime getLocalTimeFromStringAfterClock() {
    LocalTime time = getLocalTimeFromString();
    while (time.isBefore(station.getClock())) {
      stringManager.print(
          "You cannot add a train departure before the current time. Please try again.");
      time = getLocalTimeFromString();
    }
    return time;
  }

  /**
   * Checks if the user input is a String that can be parsed to a LocalTime object.
   *
   * @param inputDepartureTime The input to be checked
   * @return True if the input is a valid time, false otherwise
   */
  private boolean departureTimeValid(String inputDepartureTime) {
    try {
      LocalTime.parse(inputDepartureTime);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Runs StringManagers method printTrackAsk() and stores the user input.
   *
   * @return The track
   */
  public String getTrack() {
    stringManager.printTrackAsk();
    return scanner.nextLine();
  }

  /**
   * Asks the user for a train number. Will keep asking until the user inputs a used and valid train
   * number.
   *
   * @return The train number
   */
  public int getTrainNumberInUse() {
    int trainNumber = 0;
    do {
      stringManager.printTrainNumberAsk();
      try {
        trainNumber = scanner.nextInt();
        scanner.nextLine();

        if (trainNumber < 1) {
          stringManager.printTrainNumberInvalid();
        } else if (!station.trainExists(trainNumber)) {
          stringManager.printTrainNumberNotInUse();
          trainNumber = -1;
        }
      } catch (Exception e) {
        stringManager.printTrainNumberInvalid();
        scanner.nextLine();
      }
    } while (trainNumber < 1);
    return trainNumber;
  }

}


