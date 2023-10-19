package edu.ntnu.stud.userInterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Iterator;

public class UserInterface {

  private Station station;

  public void start(){
    System.out.println("Welcome to the train dispatch app!");
    System.out.println("The time is now " + station.getClock());
    System.out.println("Here is a list of all the trains that are yet to depart:");
    System.out.println("Train number\tLine\tDestination\tDeparture time\tTrack");
    Iterator<TrainDeparture> iterator = station.getTrainDeparturesSorted().iterator();
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      System.out.println(trainDeparture.getTrainNumber() + "\t\t\t" + trainDeparture.getLine() + "\t\t" + trainDeparture.getDestination() + "\t\t" + trainDeparture.getDepartureTime() + "\t\t" + trainDeparture.getTrack());
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
    station.sortByDepartureTime();

  }

}
