package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import java.time.LocalTime;
import java.util.List;

/**
 * Main class of the user interface.
 * <p>Handles the switch case and calls the needed methods in the StringManager and InputHandler
 * classes.</p>
 *
 * @version 0.0.1
 * @Author Sigurd Riseth
 */
public class UserInterface {

  private Station station;
  private Printer printer;
  private InputHandler inputHandler;


  /**
   * Prints all the options the user can choose from.
   * <p>Will keep running until the user exits the application</p>
   */
  public void start() {
    boolean running = true;
    while (running) {
      printer.printOptions();
      String choice = inputHandler.getStringInput();

      if (station.getAmountOfTrainDepartures() == 0 && !List.of("4", "10", "0").contains(choice)) {
        printer.printNoTrains();
        continue;
      }

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

  private boolean closeApplication() {
    printer.printCloseApp();
    return false;
  }

  private void printAllDepartures() {
    printer.printClock(station.getClock());
    printer.printAllDepartures(station.getTrainDeparturesSorted());
  }

  /**
   * Sets the clock of the station to the user input.
   */
  private void setStationClock() {
    LocalTime time = getLocalTimeFromStringAfterClock();
    if (station.setClock(time)) {
      printer.printClockChanged(time);
    } else {
      printer.printClockNotChanged();
    }
  }

  /**
   * Removes a train departure by the train number the user inputs.
   */
  private void removeTrainDepartureByTrainNumber() {
    int trainNumber = getTrainNumberInUse();
    station.removeTrainDepartureByTrainNumber(trainNumber);
    printer.printTrainRemoved();
  }

  /**
   * Prints the train departure with the train number the user inputs.
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
   * Removes the track for the train departure with the train number the user inputs.
   */
  private void removeTrackForTrainDeparture() {
    int trainNumber = getTrainNumberInUse();
    if (station.changeTrackByTrainNumber(trainNumber, "-1")) {
      printer.printTrackRemoved();
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  /**
   * Sets the track for the train departure with the train number the user inputs.
   */
  private void setTrackForTrainDeparture() {
    int trainNumber = getTrainNumberInUse();
    String track = getTrack();
    boolean changed = station.changeTrackByTrainNumber(trainNumber, track);
    if (changed) {
      printer.printTrackChanged(track);
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  /**
   * Sets the delay for a train departure.
   * <p>User inputs the train number and the delay</p>
   */
  private void setDelayForTrainDeparture() {
    int trainNumber = getTrainNumberInUse();
    LocalTime delay = getLocalTimeFromString();
    int result = station.changeDelayByTrainNumber(trainNumber, delay);
    if (result == 1) {
      printer.printTrainRemovedByDelay();
    } else if (result == 2) {
      printer.printDelayChanged();
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  private int getTrainNumberInUse() {
    int trainNumber;
    do {
      printer.printTrainNumberAsk();
      trainNumber = inputHandler.getInt();

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
   * Creates a train departure.
   * <p>User inputs the train number, line, destination, departure time and track</p>
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

  private String getTrack() {
    printer.printTrackAsk();
    return inputHandler.getStringInput();
  }

  private LocalTime getLocalTimeFromString() {
    LocalTime departureTime = null;
    while (departureTime == null) {
      printer.printTimeAsk();
      String inputDepartureTime = inputHandler.getStringInput();
      if (inputHandler.departureTimeValid(inputDepartureTime)) {
        departureTime = LocalTime.parse(inputDepartureTime);
        return departureTime;
      } else {
        printer.printTimeInvalid();
      }
    }
    return departureTime;
  }

  private LocalTime getLocalTimeFromStringAfterClock() {
    LocalTime time = getLocalTimeFromString();
    while (time.isBefore(station.getClock())) {
      printer.printBeforeClock(station.getClock());
      time = getLocalTimeFromString();
    }
    return time;
  }

  private String getDestination() {
    printer.printDestinationAsk();
    return inputHandler.getStringInputCapitalized();
  }

  private String getLine() {
    printer.printLineAsk();
    return inputHandler.getStringInput();
  }

  private int getTrainNumberUnused() {
    int trainNumber;

    do {
      printer.printTrainNumberAsk();

      trainNumber = inputHandler.getInt();

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
   * Initializes the StringManager and InputHandler classes. Creates some train departures and
   * prints a welcome message
   * </p>
   */
  public void init() {
    this.station = new Station();
    this.printer = new Printer();
    this.inputHandler = new InputHandler();
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
