package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;

public class UserInterface {

  private Station station;
  private StringManager stringManager;
  private InputHandler inputHandler;
  private final String trainNumberAsk = "Please enter a train number: ";


  public void start() {

    boolean running = true;
    while (running) {
      stringManager.printOptions();
      String choice = inputHandler.getStringInput();

      switch (choice) { // TODO: endre navn på casene til private final String PRINT_ALL_UPCOMING_DEPARTURES = "1"; osv.
        // flytte de over til en egen pakke guiutility og klasse ConfigurationOptions og gjøre de static.
        // kan da skrive "case ConfigurationOptions.PRINT_ALL_UPCOMING_DEPARTURES:"
        // skriv og static final når du initialiserer den i UI så du slipper opprette objekt.
        // kan også ha StringManager i samme pakke og gjøre det samme der. Hvor statiske strings blir lagret.
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
        case "5": //TODO: Hva skjer om man skriver inn en streng til trainnumber?
          setDelayForTrainDeparture();
          break;
        case "6": // TODO: kan ikke være 0 eller negativ
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
          stringManager.print(("Please enter a valid number. The number should be between 0 and 10"));
          break;
      }
    }
  }

  private void setStationClock() {
    LocalTime time = inputHandler.getLocalTimeFromString();
    stringManager.print(station.setClock(time));
  }

  private void removeTrainDepartureByTrainNumber() {
    int trainNumber5 = inputHandler.getTrainNumberInUse();
    station.removeTrainDepartureByTrainNumber(trainNumber5);
    stringManager.print(("Train has been removed."));
  }

  private void getTrainByTrainNumber() {
    int trainNumber3 = inputHandler.getTrainNumberInUse();
    TrainDeparture train = station.getTrainDepartureByTrainNumber(trainNumber3);
    if (train != null) {
      stringManager.printTrainDeparture(train);
    } else {
      stringManager.printTrainNumberNotInUse();
    }
  }

  private void removeTrackForTrainDeparture() {
    int trainNumber6 = inputHandler.getTrainNumberInUse();
    stringManager.print(station.changeTrackByTrainNumber(trainNumber6, "-1"));
  }

  private void setTrackForTrainDeparture() {
    int trainNumber4 = inputHandler.getTrainNumberInUse();
    String track2 = inputHandler.getTrack();
    stringManager.print((station.changeTrackByTrainNumber(trainNumber4, track2)));
  }

  private void setDelayForTrainDeparture() {
    int trainNumber2 = inputHandler.getTrainNumberInUse();
    LocalTime delay = inputHandler.getLocalTimeFromString();
    stringManager.print(station.changeDelayByTrainNumber(trainNumber2, delay));
  }

  private void printAllUpcomingDeparturesToDestination() {
    String destination1 = inputHandler.getDestination();
    stringManager.printAllDeparturesToDestination(destination1);
  }

  private void printNextDepartureToDestination() {
    String destination2 = inputHandler.getDestination();
    stringManager.printNextDepartureToDestination(destination2);
  }

  private void createTrainDeparture() {
    int trainNumber = inputHandler.getTrainNumberUnused();
    String line = inputHandler.getLine();
    String destination3 = inputHandler.getDestination();
    LocalTime departureTime = inputHandler.getLocalTimeFromStringAfterClock();
    String track = inputHandler.getTrack();

    station.createTrainDeparture(track, trainNumber, line, destination3, departureTime,
        station);
    stringManager.print("Train departure has been added!");
  }

  public void init() {
    this.station = new Station();
    this.stringManager = new StringManager(station);
    this.inputHandler = new InputHandler(stringManager);
    createTrains();
    welcomeMessage();

  }

  private void createTrains() {
    station.createTrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20), station);
    station.createTrainDeparture("2", 2, "L2", "Trondheim", LocalTime.of(5, 40), station);
    station.createTrainDeparture("3", 3, "L3", "Bergen", LocalTime.of(4, 0), station);
    station.createTrainDeparture("4", 4, "L4", "Stavanger", LocalTime.of(3, 17), station);
    station.createTrainDeparture("2", 5, "L5", "Kristiansand", LocalTime.of(5, 0), station);
    station.createTrainDeparture("3", 6, "L2", "Trondheim", LocalTime.of(4, 20), station);
  }

  private void welcomeMessage() {
    System.out.println("-------------------------------------------");
    System.out.println("Welcome to the train dispatch app!");
    System.out.println("The time is now " + station.getClock());
  }

}
