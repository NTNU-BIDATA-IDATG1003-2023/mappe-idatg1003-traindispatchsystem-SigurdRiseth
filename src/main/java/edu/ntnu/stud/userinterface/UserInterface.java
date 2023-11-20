package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;

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
  public void start() { // TODO: Remove the cases where this class sends a string to the printer class. Makes it harder to translate the application. Beacuse the strings should only be written in one class.

    boolean running = true;
    while (running) {
      printer.printOptions();
      String choice = inputHandler.getStringInput();

      switch (choice) {
        case "1":
          printAllDepartures();
          break;
        case "2":
          printAllUpcomingDeparturesToDestination();
          break;
        case "3":
          printNextDepartureToDestination();
          break;
        case "4":
          createTrainDeparture();
          break;
        case "5":
          setDelayForTrainDeparture();
          break;
        case "6":
          setTrackForTrainDeparture();
          break;
        case "7":
          removeTrackForTrainDeparture();
          break;
        case "8":
          getTrainByTrainNumber();
          break;
        case "9":
          removeTrainDepartureByTrainNumber();
          break;
        case "10":
          setStationClock();
          break;
        case "0":
          running = closeApplication();
          break;
        default:
          printer.print(
              ("Please enter a valid number. The number should be between 0 and 10"));
      }
    }
  }

  private boolean closeApplication() {
    printer.print("Thank you for using the train dispatch app!");
    printer.print("The application has now been terminated.");
    return false;
  }

  private void printAllDepartures() {
    printer.print("The time is now " + station.getClock());
    printer.printAllDepartures(station.getTrainDeparturesSorted());
  }

  /**
   * Sets the clock of the station to the user input.
   */
  private void setStationClock() {
    LocalTime time = getLocalTimeFromStringAfterClock();
    printer.print(station.setClock(time));
  }

  /**
   * Removes a train departure by the train number the user inputs.
   */
  private void removeTrainDepartureByTrainNumber() {
    int trainNumber = getTrainNumberInUse();
    station.removeTrainDepartureByTrainNumber(trainNumber);
    printer.print(("Train has been removed."));
  }

  /**
   * Prints the train departure with the train number the user inputs.
   */
  private void getTrainByTrainNumber() {
    int trainNumber = getTrainNumberInUse();
    TrainDeparture train = station.getTrainDepartureByTrainNumber(trainNumber);
    if (train != null) {
      printer.printTrainDeparture(train);
    } else {
      printer.printTrainNumberNotInUse();
    }
  }

  /**
   * Removes the track for the train departure with the train number the user inputs.
   */
  private void removeTrackForTrainDeparture() {
    int trainNumber = getTrainNumberInUse();
    printer.print(station.changeTrackByTrainNumber(trainNumber, "-1"));
  }

  /**
   * Sets the track for the train departure with the train number the user inputs.
   */
  private void setTrackForTrainDeparture() {
    int trainNumber = getTrainNumberInUse();
    String track = getTrack();
    printer.print((station.changeTrackByTrainNumber(trainNumber, track)));
  }

  /**
   * Sets the delay for a train departure.
   * <p>User inputs the train number and the delay</p>
   */
  private void setDelayForTrainDeparture() {
    int trainNumber = getTrainNumberInUse();
    LocalTime delay = getLocalTimeFromString();
    printer.print(station.changeDelayByTrainNumber(trainNumber, delay));
  }

  private int getTrainNumberInUse() {
    int trainNumber = 0;
    do {
      printer.printTrainNumberAsk();
      try {
        trainNumber = inputHandler.getInt();

        if (trainNumber < 1) {
          printer.printTrainNumberInvalid();
        } else if (!station.trainExists(trainNumber)) {
          printer.printTrainNumberNotInUse();
          trainNumber = -1;
        }
      } catch (Exception e) {
        printer.printTrainNumberInvalid();
      }
    } while (trainNumber < 1);
    return trainNumber;

  }

  /**
   * Prints all upcoming departures to the destination the user inputs.
   */
  private void printAllUpcomingDeparturesToDestination() {
    String destination = getDestination();
    printer.print("The time is now " + station.getClock());
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
      printer.print("No train to " + destination + " was found.");
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
    printer.print("Train departure has been added!");
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
      printer.print("You can not input a time before "
          + station.getClock().toString() + ". Please try again.");
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
    int trainNumber = 0;

    do {
      printer.printTrainNumberAsk();
      try {
        trainNumber = inputHandler.getInt();

        if (trainNumber < 1) {
          printer.printTrainNumberInvalid();
        } else if (station.trainExists(trainNumber)) {
          printer.printTrainNumberInUse();
          trainNumber = -1;
        }
      } catch (Exception e) {
        printer.printTrainNumberInvalid();
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
    printer.print("-------------------------------------------");
    printer.print("Welcome to the train dispatch app!");
    printer.print("The time is now " + station.getClock());
  }

}
