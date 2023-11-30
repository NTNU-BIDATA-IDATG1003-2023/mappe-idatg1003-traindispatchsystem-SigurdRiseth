package edu.ntnu.stud.userinterface;

import java.util.Scanner;

/**
 * Class for reading and returning user input.
 * <p> Uses the Scanner class to read user input. </p>
 *
 * @version 0.0.1
 * @author Sigurd Riseth
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
   * Returns the user input as an int. If the input is not an int, returns -1.
   *
   * @return The user input as an int
   */
  public int getInt() {
    int result;
    try {
      result = scanner.nextInt();
      scanner.nextLine();
    } catch (Exception e) {
      scanner.nextLine();
      result = -1;
    }
    return result;
  }
}


