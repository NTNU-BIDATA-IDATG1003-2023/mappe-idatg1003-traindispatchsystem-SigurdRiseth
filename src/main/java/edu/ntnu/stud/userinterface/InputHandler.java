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

  private final Scanner scanner = new Scanner(System.in);

  /**
   * Constructor for the InputHandler class.
   * <p>Initializes the stringManager field.</p>
   *
   * @param stringManager The StringManager object
   */
  public InputHandler() {
    // TODO document why this constructor is empty
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
   * Runs StringManagers method printLineAsk() and returns the user input.
   *
   * @return The line
   */

  /**
   * Runs StringManagers method printDestinationAsk() and stores the user input. Capitalizes the
   * first letter and lowercase the rest before returning.
   *
   * @return The destination
   */

  public String getStringInputCapitalized() {
    return scanner.nextLine().substring(0, 1).toUpperCase()
        + scanner.nextLine().substring(1).toLowerCase();
  }


  /**
   * Runs StringManagers method printTimeAsk() and stores the user input. Will keep asking until the
   * user inputs a valid time.
   *
   * @return The departure time
   */

  /**
   * Runs StringManagers method getLocalTimeFromStringAfterClock() and stores the user input. Will
   * keep asking until the user inputs a valid time after the current time.
   *
   * @return The departure time
   */

  /**
   * Checks if the user input is a String that can be parsed to a LocalTime object.
   *
   * @param inputDepartureTime The input to be checked
   * @return True if the input is a valid time, false otherwise
   */
  public boolean departureTimeValid(String inputDepartureTime) {
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


  /**
   * Asks the user for a train number. Will keep asking until the user inputs a used and valid train
   * number.
   *
   * @return The train number
   */


  public int getInt() {
    int trainNumber = scanner.nextInt();
    scanner.nextLine();
    return trainNumber;
  }
}


