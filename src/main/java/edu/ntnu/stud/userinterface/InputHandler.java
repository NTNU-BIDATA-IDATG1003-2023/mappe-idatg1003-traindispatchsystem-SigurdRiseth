package edu.ntnu.stud.userinterface;

import java.time.LocalTime;
import java.util.Scanner;

/**
 * Class for handling user input.
 * <p>Handles all user input and calls the needed methods in the StringManager class.</p>
 *
 * @version 0.0.1
 * @Author Sigurd Riseth
 */
public class InputHandler {

  private final Scanner scanner = new Scanner(System.in);

  /**
   * Gets the user input as a String.
   *
   * @return The user input as a String
   */
  public String getStringInput() {
    return scanner.nextLine();
  }


  /**
   * Gets the user input as a String. Capitalizes the first letter and makes the rest lowercase.
   *
   * @return The user input as a String with the first letter capitalized
   */
  public String getStringInputCapitalized() {
    String result = scanner.nextLine();
    result = result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
    return result;
  }

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
   * Returns the user input as an int.
   * @return
   */
  public int getInt() {
    try {
      return scanner.nextInt();
    } catch (Exception e) {
      scanner.nextLine();
      return -1;
    }
  }
}


