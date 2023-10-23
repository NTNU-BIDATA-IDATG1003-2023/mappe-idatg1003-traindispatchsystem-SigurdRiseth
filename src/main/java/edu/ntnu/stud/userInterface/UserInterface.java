package edu.ntnu.stud.userInterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Iterator;
import javax.swing.JDesktopPane;

public class UserInterface {

  private Station station;

  public void start(){
    System.out.println("Welcome to the train dispatch app!");
    System.out.println("The time is now " + station.getClock());
    System.out.println(station.setClock(LocalTime.of(3, 0)));
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
          trainDeparture.getTrack()
      );
      System.out.println(formattedLine);
    }

    String departure = station.getTrainDepartureByDestination("Trondheim").getDestination();
    System.out.println("The next train to " + station.getTrainDepartureByDestination(departure).getDestination() + " departs at " + station.getTrainDepartureByDestination(departure).getDepartureTime() + " from track " + station.getTrainDepartureByDestination(departure).getTrack());
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
    station.sortByDepartureTime();

  }

}
