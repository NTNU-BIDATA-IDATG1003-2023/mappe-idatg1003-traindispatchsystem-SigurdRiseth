package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Scanner;

public class UserInterface {

  private Station station;

  public void start(){

    Scanner scanner = new Scanner(System.in);

    boolean running = true;
    while (running) {
      System.out.println("-------------------------------------------");
      System.out.println("1: Print all departures");
      System.out.println("2: Print all departures to a given destination");
      System.out.println("3: Print the next departure to a given destination");
      System.out.println("4: Set the clock");
      System.out.println("5: Exit");
      System.out.println("Please enter a number between 1 and 5:");
      String input = scanner.nextLine();

      switch (input) {
        case "1":
          printAllDepartures();
          break;
        case "2":
          System.out.println("Please enter a destination:");
          break;
        case "3":
          System.out.println("Please enter a destination:");
          String destination = scanner.nextLine();
          System.out.println(
              "The next train to " + station.getTrainDepartureByDestination(destination).getDestination()
                  + " departs at " + station.getTrainDepartureByDestination(destination)
                  .getDepartureTime() + " from track " + station.getTrainDepartureByDestination(
                  destination).getTrack());
          break;
        case "4":
          System.out.println("Please enter a time in the format hh:mm");
          String time = scanner.nextLine();
          System.out.println(station.setClock(time));
          break;
        case "5":
          running = false;
          System.out.println("Thank you for using the train dispatch app!");
          System.out.println("The application has now been terminated.");
          break;
        default:
          System.out.println("Please enter a valid number. The number should be between 1 and 5");
          break;
      }
    }
  }

  private void printAllDepartures() {
    System.out.println("Here is a list of all the trains that are yet to depart:");
    System.out.println("Train number\tLine\tDestination\t\t\tDeparture time\tTrack");
    Iterator<TrainDeparture> iterator = station.getTrainDeparturesSorted().iterator();
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      String formattedLine = String.format("%-15s%-5s%-20s%-15s%-10s",
          trainDeparture.getTrainNumber(),
          trainDeparture.getLine(),
          trainDeparture.getDestination(),
          trainDeparture.getDepartureTime(),
          trainDeparture.getTrack() // TODO: Hvis track == -1 skal "-" vises i tavlen
      );
      System.out.println(formattedLine);
    }
  }

  public void init(){
    this.station = new Station();
    TrainDeparture trainDeparture = new TrainDeparture(1, 1, "L1", "Oslo", LocalTime.of(5, 20), station);
    station.addTrainDeparture(trainDeparture);
    TrainDeparture trainDeparture2 = new TrainDeparture(2, 2, "L2", "Trondheim", LocalTime.of(5, 40), station);
    station.addTrainDeparture(trainDeparture2);
    TrainDeparture trainDeparture3 = new TrainDeparture(3, 3, "L3", "Bergen", LocalTime.of(4, 0), station);
    station.addTrainDeparture(trainDeparture3);
    TrainDeparture trainDeparture4 = new TrainDeparture(4, 4, "L4", "Stavanger", LocalTime.of(3, 17), station);
    station.addTrainDeparture(trainDeparture4);
    TrainDeparture trainDeparture5 = new TrainDeparture(5, 5, "L5", "Kristiansand", LocalTime.of(5, 0), station);
    station.addTrainDeparture(trainDeparture5);
    trainDeparture2.setDelay(LocalTime.of(0, 20), station);
    TrainDeparture trainDeparture6 = new TrainDeparture(3, 6, "L2", "Trondheim", LocalTime.of(4, 20), station);
    station.addTrainDeparture(trainDeparture6);
    station.sortByDepartureTime();

    System.out.println("-------------------------------------------");
    System.out.println("Welcome to the train dispatch app!");
    System.out.println("The time is now " + station.getClock());

  }

}
