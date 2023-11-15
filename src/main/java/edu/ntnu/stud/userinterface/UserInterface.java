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
          stringManager.printAllDepartures();
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

  /**
   * Sets the clock of the station to the user input.
   */
  private void setStationClock() {
    LocalTime time = inputHandler.getLocalTimeFromStringAfterClock();
    stringManager.print(station.setClock(time));
  }

  /**
   * Removes a train departure by the train number the user inputs.
   */
  private void removeTrainDepartureByTrainNumber() {
    int trainNumber5 = inputHandler.getTrainNumberInUse();
    station.removeTrainDepartureByTrainNumber(trainNumber5);
    stringManager.print(("Train has been removed."));
  }

  /**
   * Prints the train departure with the train number the user inputs.
   */
  private void getTrainByTrainNumber() {
    int trainNumber3 = inputHandler.getTrainNumberInUse();
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
    int trainNumber6 = inputHandler.getTrainNumberInUse();
    stringManager.print(station.changeTrackByTrainNumber(trainNumber6, "-1"));
  }

  /**
   * Sets the track for the train departure with the train number the user inputs.
   */
  private void setTrackForTrainDeparture() {
    int trainNumber4 = inputHandler.getTrainNumberInUse();
    String track2 = inputHandler.getTrack();
    stringManager.print((station.changeTrackByTrainNumber(trainNumber4, track2)));
  }

  /**
   * Sets the delay for a train departure.
   * <p>User inputs the train number and the delay</p>
   */
  private void setDelayForTrainDeparture() {
    int trainNumber2 = inputHandler.getTrainNumberInUse();
    LocalTime delay = inputHandler.getLocalTimeFromString();
    stringManager.print(station.changeDelayByTrainNumber(trainNumber2, delay));
  }

  /**
   * Prints all upcoming departures to the destination the user inputs.
   */
  private void printAllUpcomingDeparturesToDestination() {
    String destination1 = inputHandler.getDestination();
    stringManager.printAllDeparturesToDestination(destination1);
  }

  /**
   * Prints the next departure to the destination the user inputs.
   */
  private void printNextDepartureToDestination() {
    String destination2 = inputHandler.getDestination();
    stringManager.printNextDepartureToDestination(destination2);
  }

  /**
   * Creates a train departure.
   * <p>User inputs the train number, line, destination, departure time and track</p>
   */
  private void createTrainDeparture() {
    int trainNumber = inputHandler.getTrainNumberUnused();
    String line = inputHandler.getLine();
    String destination3 = inputHandler.getDestination();
    LocalTime departureTime = inputHandler.getLocalTimeFromStringAfterClock();
    String track = inputHandler.getTrack();

    station.createTrainDeparture(track, trainNumber, line, destination3, departureTime);
    stringManager.print("Train departure has been added!");
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
    this.stringManager = new StringManager(station);
    this.inputHandler = new InputHandler(stringManager, station);
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
   * Returns the station.
   *
   * @return station
   */
  public Station getStation() {
    return station;
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
