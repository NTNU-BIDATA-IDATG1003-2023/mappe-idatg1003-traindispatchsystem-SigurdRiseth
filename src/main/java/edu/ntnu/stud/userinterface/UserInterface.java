package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Scanner;

public class UserInterface {

  private Station station;
  private StringManager stringManager;

  public void start() {

    Scanner scanner = new Scanner(System.in);

    String trainNumberAsk = "Please enter a train number: ";
    String destinationAsk = "Please enter a destination: ";

    boolean running = true;
    while (running) {
      System.out.println(stringManager.options());
      String input = scanner.nextLine();

      switch (input) {
        case "1":
          System.out.println(stringManager.getAllDepartures());
          break;
        case "2":
          System.out.println(destinationAsk);
          String destination1 = scanner.nextLine();
          System.out.println(stringManager.printAllDeparturesToDestination(destination1));
          break;
        case "3":
          System.out.println(destinationAsk);
          String destination2 = scanner.nextLine();
          System.out.println(stringManager.getNextDepartureToDestination(destination2));
          break;
        case "4":
          int trainNumber = -1;
          boolean flag = true;
          String departureTime = null;

          while (flag) {
            System.out.println(trainNumberAsk);
            trainNumber = scanner.nextInt();

            if (trainNumber < 1) {
              System.out.println(
                  "The train number must be a whole number above 0. Please try again.");
            } else if (!station.trainExists(trainNumber)) {
              flag = false;
            } else {
              System.out.println("Train number already exists. Please try again.");
            }
          }

          scanner.nextLine();
          System.out.println("Please enter a line:");
          String line = scanner.nextLine();
          System.out.println(destinationAsk);
          String destination3 = scanner.nextLine();
          destination3 = destination3.substring(0, 1).toUpperCase()
              + destination3.substring(1).toLowerCase();
          while (!flag) {
            System.out.println("Please enter a departure time (hh:mm):");
            departureTime = scanner.nextLine();
            try {
              LocalTime.parse(departureTime);
              flag = true;
            } catch (Exception e) {
              System.out.println("Please try again and enter a valid time.");
            }
          }
          System.out.println(
              "Please enter a track (type \"none\" if you do not wish to assign one):");
          String track = scanner.nextLine();
          TrainDeparture trainDeparture = new TrainDeparture(track, trainNumber, line, destination3,
              LocalTime.parse(departureTime), station);
          station.addTrainDeparture(trainDeparture);
          System.out.println("Train departure has been added!");
          break;

        case "5":
          System.out.println("Please enter a train number:");
          int trainNumber2 = scanner.nextInt();
          scanner.nextLine();
          System.out.println("Please enter a delay (hh:mm):");
          String delay = scanner.nextLine();
          try {
            LocalTime.parse(delay);
          } catch (Exception e) {
            System.out.println("Please try again and enter a valid time.");
            break;
          }
          System.out.println(
              station.changeDelayByTrainNumber(trainNumber2, LocalTime.parse(delay)));
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
          System.out.println("Please enter a train number:");
          int trainNumber5 = scanner.nextInt();
          scanner.nextLine();
          station.removeTrainDepartureByTrainNumber(trainNumber5);
          System.out.println("Train has been removed.");
          break;
        case "10":
          System.out.println("Please enter a time in the format hh:mm");
          String time = scanner.nextLine();
          System.out.println(station.setClock(time));
          break;
        case "0":
          running = false;
          System.out.println("Thank you for using the train dispatch app!");
          System.out.println("The application has now been terminated.");
          break;
        default:
          System.out.println("Please enter a valid number. The number should be between 1 and 10");
          break;
      }
    }
  }

  public void init() {
    this.station = new Station();
    this.stringManager = new StringManager(station);
    createTrains();
    welcomeMessage();

  }

  private void createTrains() {
    TrainDeparture trainDeparture1 = new TrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20),
        station);
    station.addTrainDeparture(trainDeparture1);
    TrainDeparture trainDeparture2 = new TrainDeparture("2", 2, "L2", "Trondheim",
        LocalTime.of(5, 40), station);
    station.addTrainDeparture(trainDeparture2);
    TrainDeparture trainDeparture3 = new TrainDeparture("3", 3, "L3", "Bergen", LocalTime.of(4, 0),
        station);
    station.addTrainDeparture(trainDeparture3);
    TrainDeparture trainDeparture4 = new TrainDeparture("4", 4, "L4", "Stavanger",
        LocalTime.of(3, 17), station);
    station.addTrainDeparture(trainDeparture4);
    TrainDeparture trainDeparture5 = new TrainDeparture("5", 5, "L5", "Kristiansand",
        LocalTime.of(5, 0), station);
    station.addTrainDeparture(trainDeparture5);
    trainDeparture2.setDelay(LocalTime.of(0, 20));
    TrainDeparture trainDeparture6 = new TrainDeparture("3", 6, "L2", "Trondheim",
        LocalTime.of(4, 20), station);
    station.addTrainDeparture(trainDeparture6);
  }

  private void welcomeMessage() {
    System.out.println("-------------------------------------------");
    System.out.println("Welcome to the train dispatch app!");
    System.out.println("The time is now " + station.getClock());
  }

}
