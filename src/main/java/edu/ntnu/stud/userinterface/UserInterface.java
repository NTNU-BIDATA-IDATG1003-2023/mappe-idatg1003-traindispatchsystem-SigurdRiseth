package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;

/**
 * Main class of the user interface.
 * <p>Handles the switch case and calls the needed methods in the StringManager and InputHandler
 * classes.</p>
 *
 * @Author Sigurd Riseth
 * @version 0.0.1
 */
public class UserInterface {

  private Station station;
  private StringManager stringManager;
  private InputHandler inputHandler;


  /**
   * Prints all the options the user can choose from.
   * <p>Will keep running until the user exits the application</p>
   */
  public void start() {

    boolean running = true;
    while (running) {
      stringManager.printOptions();
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
          running = false;
          stringManager.print("Thank you for using the train dispatch app!");
          stringManager.print("The application has now been terminated.");
          break;
        default:
          stringManager.print(
              ("Please enter a valid number. The number should be between 0 and 10"));
      }
    }
  }

  private void printAllDepartures() {
    stringManager.print("The time is now " + station.getClock());
    stringManager.printAllDepartures(station.getTrainDeparturesSorted());
  }

  /**
   * Sets the clock of the station to the user input.
   */
  private void setStationClock() {
    LocalTime time = getLocalTimeFromStringAfterClock();
    stringManager.print(station.setClock(time));
  }

  /**
   * Removes a train departure by the train number the user inputs.
   */
  private void removeTrainDepartureByTrainNumber() {
    int trainNumber5 = getTrainNumberInUse();
    station.removeTrainDepartureByTrainNumber(trainNumber5);
    stringManager.print(("Train has been removed."));
  }

  /**
   * Prints the train departure with the train number the user inputs.
   */
  private void getTrainByTrainNumber() {
    int trainNumber3 = getTrainNumberInUse();
    TrainDeparture train = station.getTrainDepartureByTrainNumber(trainNumber3);
    if (train != null) {
      stringManager.printTrainDeparture(train);
    } else {
      stringManager.printTrainNumberNotInUse();
    }
  }

  /**
   * Removes the track for the train departure with the train number the user inputs.
   */
  private void removeTrackForTrainDeparture() {
    int trainNumber6 = getTrainNumberInUse();
    stringManager.print(station.changeTrackByTrainNumber(trainNumber6, "-1"));
  }

  /**
   * Sets the track for the train departure with the train number the user inputs.
   */
  private void setTrackForTrainDeparture() {
    int trainNumber4 = getTrainNumberInUse();
    String track2 = getTrack();
    stringManager.print((station.changeTrackByTrainNumber(trainNumber4, track2)));
  }

  /**
   * Sets the delay for a train departure.
   * <p>User inputs the train number and the delay</p>
   */
  private void setDelayForTrainDeparture() {
    int trainNumber2 = getTrainNumberInUse();
    LocalTime delay = getLocalTimeFromString();
    stringManager.print(station.changeDelayByTrainNumber(trainNumber2, delay));
  }

  private int getTrainNumberInUse() {
    int trainNumber = 0;
    do {
      stringManager.printTrainNumberAsk();
      try {
        trainNumber = inputHandler.getInt();

        if (trainNumber < 1) {
          stringManager.printTrainNumberInvalid();
        } else if (!station.trainExists(trainNumber)) {
          stringManager.printTrainNumberNotInUse();
          trainNumber = -1;
        }
      } catch (Exception e) {
        stringManager.printTrainNumberInvalid();
      }
    } while (trainNumber < 1);
    return trainNumber;

  }

  /**
   * Prints all upcoming departures to the destination the user inputs.
   */
  private void printAllUpcomingDeparturesToDestination() {
    String destination1 = getDestination();
    stringManager.print("The time is now " + station.getClock());
    stringManager.printAllDeparturesToDestination(destination1, station.getTrainDeparturesSorted());
  }

  /**
   * Prints the next departure to the destination the user inputs.
   */
  private void printNextDepartureToDestination() {
    String destination2 = getDestination();
    if (station.getTrainDepartureByDestination(destination2) != null) {
      stringManager.printNextDepartureToDestination(station.getTrainDepartureByDestination(destination2));
    } else {
      stringManager.print("No train to " + destination2 + " was found.");
    }

  }

  /**
   * Creates a train departure.
   * <p>User inputs the train number, line, destination, departure time and track</p>
   */
  private void createTrainDeparture() {

    int trainNumber = getTrainNumberUnused();
    String line = getLine();
    String destination3 = getDestination();
    LocalTime departureTime = getLocalTimeFromStringAfterClock();
    String track = getTrack();

    station.createTrainDeparture(track, trainNumber, line, destination3, departureTime);
    stringManager.print("Train departure has been added!");
  }

  private String getTrack() {
    stringManager.printTrackAsk();
    return inputHandler.getStringInput();
  }

  private LocalTime getLocalTimeFromString() {
    LocalTime departureTime = null;
    while (departureTime == null) {
      stringManager.printTimeAsk();
      String inputDepartureTime = inputHandler.getStringInput();
      if (inputHandler.departureTimeValid(inputDepartureTime)) {
        departureTime = LocalTime.parse(inputDepartureTime);
        return departureTime;
      } else {
        stringManager.printTimeInvalid();
      }
    }
    return departureTime;
  }

  private LocalTime getLocalTimeFromStringAfterClock() {
    LocalTime time = getLocalTimeFromString();
    while (time.isBefore(station.getClock())) {
      stringManager.print("You can not input a time before "
          + station.getClock().toString() + ". Please try again.");
      time = getLocalTimeFromString();
    }
    return time;
  }

  private String getDestination() {
    stringManager.printDestinationAsk();
    return inputHandler.getStringInputCapitalized();
  }

  private String getLine() {
    stringManager.printLineAsk();
    return inputHandler.getStringInput();
  }

  private int getTrainNumberUnused() {
    int trainNumber = 0;

    do {
      stringManager.printTrainNumberAsk();
      try {
        trainNumber = inputHandler.getInt();

        if (trainNumber < 1) {
          stringManager.printTrainNumberInvalid();
        } else if (station.trainExists(trainNumber)) {
          stringManager.printTrainNumberInUse();
          trainNumber = -1;
        }
      } catch (Exception e) {
        stringManager.printTrainNumberInvalid();
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
    this.stringManager = new StringManager();
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
    stringManager.print("-------------------------------------------");
    stringManager.print("Welcome to the train dispatch app!");
    stringManager.print("The time is now " + station.getClock());
  }

}
