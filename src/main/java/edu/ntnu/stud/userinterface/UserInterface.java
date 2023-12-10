package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import java.time.LocalTime;
import java.util.List;

/**
 * The UserInterface class serves as the main interface for user interactions in the Train Dispatch Application.
 *
 * <p>
 * This class leverages the Printer class for output, InputReader for user input,
 * and Station for managing train-related data. The user interface
 * features a menu-driven system with a switch case for different user choices, enhancing
 * user-friendliness.
 * </p>
 * <p>
 * Additionally, the class includes methods for creating, modifying, and displaying train departures
 * with proper error handling. It emphasizes validation of user inputs and ensures that operations
 * are executed only under appropriate conditions.
 * </p>
 *
 * @author Sigurd Riseth
 * @version 0.0.1
 * @since 14.10.2023
 * @see Printer
 * @see InputReader
 * @see Station
 */
public class UserInterface {

  private Station station;
  private Printer printer;
  private InputReader inputReader;


  /**
   * Prints all the options the user can choose from, and runs the corresponding case according to
   * the user input.
   * <p>
   * If there are no trains created in the station, the only options available to the user will be
   * 4, 10 and 0. This method will keep running until the user exits the application.
   * </p>
   */
  public void start() {
    boolean running = true;
    while (running) {
      boolean noTrains = false;
      printer.printOptions();
      String choice = inputReader.getStringInput();

      if (station.getAmountOfTrainDeparturesToDepart() == 0 && !List.of("4", "10", "0")
          .contains(choice)) {
        printer.printNoTrains();
        noTrains = true;
      }

      if (!noTrains) {
        switch (choice) {
          case "1" -> printAllDepartures();
          case "2" -> printAllUpcomingDeparturesToDestination();
          case "3" -> printNextDepartureToDestination();
          case "4" -> createTrainDeparture();
          case "5" -> setDelayForTrainDeparture();
          case "6" -> setTrackForTrainDeparture();
          case "7" -> removeTrackForTrainDeparture();
          case "8" -> getTrainByTrainNumber();
          case "9" -> removeTrainDepartureByTrainNumber();
          case "10" -> setStationClock();
          case "0" -> running = closeApplication();
          default -> printer.printInvalidChoice();
        }
      }
    }
  }

  /**
   * Closes the application, and prints a message.
   * <p>
   * Returns false to the start() method, which will then stop the while loop and exit the
   * application.
   * </p>
   *
   * @return false
   */
  private boolean closeApplication() {
    printer.printCloseApp();
    return false;
  }

  /**
   * Prints the clock and all the train departures in the station.
   */
  private void printAllDepartures() {
    printer.printClock(station.getClock());
    printer.printAllDepartures(station.getTrainDeparturesSorted());
  }

  /**
   * Sets the clock of the station to the user input.
   * <p>
   * The clock is only changed if the input time is after the current time.
   * </p>
   */
  private void setStationClock() {
    LocalTime time = getLocalTimeFromStringAfterClock();
    station.setClock(time);
    if (station.getClock() == time) {
      printer.printClockChanged(time);
    } else {
      printer.printClockNotChanged();
    }
  }

  /**
   * Asks for a train number in use and removes the corresponding train departure.
   * <p>
   * Checks if the train is removed and prints a message accordingly.
   * </p>
   */
  private void removeTrainDepartureByTrainNumber() {
    int trainNumber = getTrainNumberInUse();
    station.removeTrainDepartureByTrainNumber(trainNumber);
    if (!station.trainExists(trainNumber)) {
      printer.printTrainRemoved();
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  /**
   * Asks the user for a train number in use and prints the corresponding train departure.
   */
  private void getTrainByTrainNumber() {
    int trainNumber = getTrainNumberInUse();
    if (station.trainExists(trainNumber)) {
      printer.printTrainDeparture(station.getTrainDepartureByTrainNumber(trainNumber));
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  /**
   * Removes the track for an existing train departure.
   *
   * <p>
   * Asks the user for a train number. Then changes the track to -1 for the train departure with the
   * train number. The track value -1 represents "unassigned track" and will not be showed in the
   * train table.
   * </p>
   */
  private void removeTrackForTrainDeparture() { //TODO: blir det sjekket dobbelt om tog finnes?
    int trainNumber = getTrainNumberInUse();
    station.changeTrackByTrainNumber(trainNumber, "-1");
    if (station.getTrainDepartureByTrainNumber(trainNumber).getTrack() == -1) {
      printer.printTrackRemoved();
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  /**
   * Asks the user for a train number and a track. Then calls the method to set the track for a
   * train departure in the Station class.
   */
  private void setTrackForTrainDeparture() { //TODO: blir det sjekket dobbelt om tog finnes?
    int trainNumber = getTrainNumberInUse();
    String track = getTrack();
    station.changeTrackByTrainNumber(trainNumber, track);
    if (String.valueOf(station.getTrainDepartureByTrainNumber(trainNumber).getTrack())
        .equals(track)) {
      printer.printTrackChanged(track);
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  /**
   * Sets the delay for an existing train departure.
   *
   * <p>
   * User inputs the train number and the delay, the changeDelayByTrainNumber() method in the
   * Station class is then run with these parameters. A message is then printed depending on the
   * outcome of the change.
   * </p>
   */
  private void setDelayForTrainDeparture() {
    int trainNumber = getTrainNumberInUse();
    LocalTime delay = getLocalTimeFromString();
    station.changeDelayByTrainNumber(trainNumber, delay);
    if (!station.trainExists(trainNumber)) {
      printer.printTrainRemovedByDelay();
    } else if (station.getTrainDepartureByTrainNumber(trainNumber).getDelay().equals(delay)) {
      printer.printDelayChanged();
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  /**
   * Asks the user for a train number in use.
   *
   * <p>
   *   Checks if the train number is valid and in use, and if it is not, the user is asked to input
   *   a new train number.
   * </p>
   *
   * @return the train number the user inputs
   */
  private int getTrainNumberInUse() {
    int trainNumber;
    do {
      printer.printTrainNumberAsk();
      trainNumber = inputReader.getInt();

      if (trainNumber < 1) {
        printer.printTrainNumberInvalid();
      } else if (!station.trainExists(trainNumber)) {
        printer.printTrainNumberNotInUse();
        trainNumber = -1;
      }
    } while (trainNumber < 1);
    return trainNumber;
  }

  /**
   * Prints all upcoming departures to the destination the user inputs.
   */
  private void printAllUpcomingDeparturesToDestination() {
    String destination = getDestination();
    printer.printClock(station.getClock());
    printer.printAllDeparturesToDestination(destination, station.getTrainDeparturesSorted());
  }

  /**
   * Prints the next departure to the destination the user inputs.
   * <p>If there are no departures to the destination, a message is printed.</p>
   */
  private void printNextDepartureToDestination() {
    String destination = getDestination();
    if (station.getTrainDepartureByDestination(destination) != null) {
      printer.printTrainDeparture(
          station.getTrainDepartureByDestination(destination));
    } else {
      printer.printNoTrainFound(destination);
    }

  }

  /**
   * Creates a new train departure.
   * <p>User inputs the train number, line, destination, departure time and track.
   * A new train departure is then created using the createTrainDeparture() method in the Station
   * class.</p>
   *
   * @see Station
   */
  private void createTrainDeparture() {

    int trainNumber = getTrainNumberUnused();
    String line = getLine();
    String destination = getDestination();
    LocalTime departureTime = getLocalTimeFromStringAfterClock();
    String track = getTrack();

    station.createTrainDeparture(track, trainNumber, line, destination, departureTime);
    printer.printTrainAdded();
  }

  /**
   * Asks the user for a track and returns the input.
   *
   * @return the user input
   */
  private String getTrack() {
    printer.printTrackAsk();
    return inputReader.getStringInput();
  }

  /**
   * Asks the user for a time and returns the input.
   * <p>Checks if the input is valid, and if it is not, the user is asked to input a new time.</p>
   *
   * @return the user input in LocalTime format
   */
  private LocalTime getLocalTimeFromString() { // TODO: forbere denne while loopen
    LocalTime result = null;
    boolean parseableInput = false;
    while (!parseableInput) {
      printer.printTimeAsk();
      try {
        result = LocalTime.parse(inputReader.getStringInput());
        parseableInput = true;
      } catch (Exception e) {
        printer.printTimeInvalid();
      }
    }
    return result;
  }

  /**
   * Asks the user for a time using the getLocalTimeFromString() method.
   * <p>Checks if the input time is after the current time, and if it is not, the user is asked to
   * input a new time.</p>
   *
   * @return the user input time after the current time
   */
  private LocalTime getLocalTimeFromStringAfterClock() {
    LocalTime time = getLocalTimeFromString();
    while (!time.isAfter(station.getClock())) {
      printer.printBeforeClock(station.getClock());
      time = getLocalTimeFromString();
    }
    return time;
  }

  /**
   * Asks the user for a destination and returns the input.
   * <p>The returned String will have a capitalized first letter and the rest in lowercase.</p>
   *
   * @return the user input
   */
  private String getDestination() {
    printer.printDestinationAsk();
    return inputReader.getStringInputCapitalized();
  }

  /**
   * Asks the user for a line and returns the input.
   *
   * @return the user input
   */
  private String getLine() {
    printer.printLineAsk();
    return inputReader.getStringInput();
  }

  /**
   * Asks the user for a unique train number and returns the input.
   * <p>Checks if the input is valid and not in use. If the train number is in use, the user is
   * asked to input a new train number.</p>
   *
   * @return the user input
   */
  private int getTrainNumberUnused() {
    int trainNumber;

    do {
      printer.printTrainNumberAsk();

      trainNumber = inputReader.getInt();

      if (trainNumber < 1) {
        printer.printTrainNumberInvalid();
      } else if (station.trainExists(trainNumber)) {
        printer.printTrainNumberInUse();
        trainNumber = -1;
      }
    } while (trainNumber < 1);
    return trainNumber;
  }

  /**
   * First method to be called when the application starts.
   * <p>
   * Initializes the StringManager, Station and InputReader classes. Creates some train departures
   * and prints a welcome message
   * </p>
   */
  public void init() {
    this.station = new Station();
    this.printer = new Printer();
    this.inputReader = new InputReader();
    createTrains();
    welcomeMessage();

  }

  /**
   * Creates some train departures.
   */
  private void createTrains() {
    station.createTrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20));
    station.createTrainDeparture("2", 2, "L2", "Trondheim", LocalTime.of(5, 40));
    station.createTrainDeparture("3", 3, "L3", "Bergen", LocalTime.of(4, 0));
    station.createTrainDeparture("4", 4, "L4", "Stavanger", LocalTime.of(3, 17));
    station.createTrainDeparture("2", 5, "L5", "Kristiansand", LocalTime.of(5, 0));
    station.createTrainDeparture("3", 6, "L2", "Trondheim", LocalTime.of(4, 20));
  }

  /**
   * Prints a welcome message and the current time.
   */
  private void welcomeMessage() {
    printer.printWelcomeMessage();
    printer.printClock(station.getClock());
  }

}
