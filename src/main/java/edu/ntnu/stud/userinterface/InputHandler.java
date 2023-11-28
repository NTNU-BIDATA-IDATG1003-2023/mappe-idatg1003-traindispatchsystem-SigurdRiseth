package edu.ntnu.stud.userinterface;

import java.time.LocalTime;
import java.util.Scanner;

/**
 * Class for reading and returning user input.
 * <p> Uses the Scanner class to read user input. </p>
 *
 * @version 0.0.1
 * @Author Sigurd Riseth
 * @see Scanner
 */
public class InputHandler {

  private final Scanner scanner;

  public InputHandler() {
    scanner = new Scanner(System.in);
  }

  /**
   * Returns the user input as a String.
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
   * <p>Returns true if the input is a valid String, false otherwise. A valid string is in the format hh:mm. </p>
   *
   * @param inputDepartureTime The input to be checked
   * @return True if the input is a valid time, false otherwise
   * @see LocalTime
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
   * Returns the user input as an int. If the input is not an int, returns -1.
   *
   * @return The user input as an int
   */
  public int getInt() {
    try {
      int result = scanner.nextInt();
      scanner.nextLine();
      return result;
    } catch (Exception e) {
      scanner.nextLine();
      return -1;
    }
  }
}


