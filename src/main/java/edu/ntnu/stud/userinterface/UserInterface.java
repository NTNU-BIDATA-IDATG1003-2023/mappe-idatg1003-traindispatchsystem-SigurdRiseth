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
      String input = inputHandler.getStringInput();

      switch (input) { // TODO: endre navn på casene til private final String PRINT_ALL_UPCOMING_DEPARTURES = "1"; osv.
        // flytte de over til en egen pakke guiutility og klasse ConfigurationOptions og gjøre de static.
        // kan da skrive "case ConfigurationOptions.PRINT_ALL_UPCOMING_DEPARTURES:"
        // skriv og static final når du initialiserer den i UI så du slipper opprette objekt.
        // kan også ha StringManager i samme pakke og gjøre det samme der. Hvor statiske strings blir lagret.
        case "1":
          stringManager.printAllDepartures();
          break;
        case "2":
          stringManager.printDestinationAsk();
          String destination1 = inputHandler.getStringInput();
          stringManager.printAllDeparturesToDestination(destination1);
          break;
        case "3":
          stringManager.printDestinationAsk();
          String destination2 = inputHandler.getStringInput();
          stringManager.printNextDepartureToDestination(destination2);
          break;
        case "4":
          int trainNumber = getTrainNumberUnused(scanner);

          scanner.nextLine();
          System.out.println("Please enter a line:");
          String line = scanner.nextLine();
          System.out.println(destinationAsk);
          String destination3 = scanner.nextLine();
          destination3 = destination3.substring(0, 1).toUpperCase()
              + destination3.substring(1).toLowerCase();

          LocalTime departureTime = getLocalTimeFromString(scanner);

          System.out.println(
              "Please enter a track (type \"none\" if you do not wish to assign one):");
          String track = scanner.nextLine();
          station.createTrainDeparture(track, trainNumber, line, destination3, departureTime,
              station);
          System.out.println("Train departure has been added!");
          break;

        case "5":
          int trainNumber2 = getTrainNumberInUse(scanner);
          LocalTime delay = getLocalTimeFromString(scanner);
          System.out.println(station.changeDelayByTrainNumber(trainNumber2, delay));
          break;

        case "6": // TODO: kan ikke være 0 eller negativ
          System.out.println(trainNumberAsk);
          int trainNumber4 = scanner.nextInt();
          scanner.nextLine();
          System.out.println("Please enter a track:");
          String track2 = scanner.nextLine();
          System.out.println(station.changeTrackByTrainNumber(trainNumber4, track2));
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

  private LocalTime getLocalTimeFromString(Scanner scanner) {
    LocalTime departureTime = null;
    while (departureTime == null) {
      System.out.println("Please enter time in format (hh:mm): ");
      String inputDepartureTime = scanner.nextLine();
      if (departureTimeValid(inputDepartureTime)) {
        departureTime = LocalTime.parse(inputDepartureTime);
        return departureTime;
      } else {
        System.out.println("Please try again and enter a valid time.");
      }
    }
    return departureTime;
  }

  private int getTrainNumberInUse(Scanner scanner) {
    int trainNumber = -1;

    while (trainNumber < 1) {
      System.out.println(trainNumberAsk);
      trainNumber = scanner.nextInt();
      scanner.nextLine();

      if (trainNumber < 1) {
        System.out.println(
            "The train number must be a whole number above 0. Please try again.");
      } else if (!station.trainExists(trainNumber)) {
        System.out.println("The train number does not exist. Please try again.");
        trainNumber = -1;
      }
    }
    return trainNumber;
  }


  private int getTrainNumberUnused(Scanner scanner) {
    int trainNumber = -1;

    while (trainNumber < 1) {
      System.out.println(trainNumberAsk);
      trainNumber = scanner.nextInt();

      if (trainNumber < 1) {
        System.out.println(
            "The train number must be a whole number above 0. Please try again.");
      } else if (station.trainExists(trainNumber)) {
        System.out.println("The train number is already in use. Please try again.");
        trainNumber = -1;
      }
    }
    return trainNumber;
  }

  private boolean departureTimeValid(String inputDepartureTime) {
    try {
      LocalTime.parse(inputDepartureTime);
      return true;
    } catch (Exception e) {
      return false;
    }
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
