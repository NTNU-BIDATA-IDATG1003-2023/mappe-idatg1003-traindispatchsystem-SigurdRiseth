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
          printAllUpcomingDepartures();
          break;
        case "3":
          printNextDeparture();
          break;
        case "4":
          createTrainDeparture();
          break;
        case "5": //TODO: Hva skjer om man skriver inn en streng til trainnumber?
          setDelayForTrainDeparture();
          break;
        case "6": // TODO: kan ikke være 0 eller negativ
          int trainNumber4 = inputHandler.getTrainNumberInUse();
          String track2 = inputHandler.getTrack();
          stringManager.print((station.changeTrackByTrainNumber(trainNumber4, track2)));
          break;
        case "7":
          System.out.println(trainNumberAsk);
          int trainNumber6 = scanner.nextInt();
          scanner.nextLine();
          System.out.println(station.changeTrackByTrainNumber(trainNumber6, "-1"));
          break;
        case "8":
          System.out.println(trainNumberAsk);
          int trainNumber3 = scanner.nextInt();
          scanner.nextLine();
          TrainDeparture train = station.getTrainDepartureByTrainNumber(trainNumber3);
          if (train != null) {
            System.out.println("Train number: " + train.getTrainNumber());
            System.out.println("Line: " + train.getLine());
            System.out.println("Destination: " + train.getDestination());
            System.out.println("Departure time: " + train.getDepartureTime());
            System.out.println("Track: " + train.getTrack());
          } else {
            System.out.println("Train does not exist. Please try again.");
          }
          break;
        case "9":
          System.out.println(trainNumberAsk);
          int trainNumber5 = scanner.nextInt();
          scanner.nextLine();
          station.removeTrainDepartureByTrainNumber(trainNumber5);
          System.out.println("Train has been removed.");
          break;
        case "10":
          LocalTime time = getLocalTimeFromString(scanner);
          System.out.println(station.setClock(time));
          break;
        case "0":
          running = false;
          System.out.println("Thank you for using the train dispatch app!");
          System.out.println("The application has now been terminated.");
          break;
        default:
          System.out.println("Please enter a valid number. The number should be between 0 and 10");
          break;
      }
    }
  }

  private void setDelayForTrainDeparture() {
    int trainNumber2 = inputHandler.getTrainNumberUnused();
    LocalTime delay = inputHandler.getLocalTimeFromString();
    stringManager.print(station.changeDelayByTrainNumber(trainNumber2, delay));
  }

  private void printAllUpcomingDepartures() {
    stringManager.printDestinationAsk();
    String destination1 = inputHandler.getStringInput();
    stringManager.printAllDeparturesToDestination(destination1);
  }

  private void printNextDeparture() {
    stringManager.printDestinationAsk();
    String destination2 = inputHandler.getStringInput();
    stringManager.printNextDepartureToDestination(destination2);
  }

  private void createTrainDeparture() {
    int trainNumber = inputHandler.getTrainNumberUnused();
    String line = inputHandler.getLine();
    String destination3 = inputHandler.getDestination();
    LocalTime departureTime = inputHandler.getLocalTimeFromString();
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
