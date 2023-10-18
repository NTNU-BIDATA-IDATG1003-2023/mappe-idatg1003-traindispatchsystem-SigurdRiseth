package edu.ntnu.stud;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {

  // TODO: Fill in the main method and any other methods you need.

  private Station station;
  private TrainDeparture trainDeparture1;
  private TrainDeparture trainDeparture2;

  public void initialize(){
    station = new Station();
    TrainDeparture trainDeparture1 = new TrainDeparture(1, 1, "L1", "Oslo", LocalTime.of(0, 0), station);
    TrainDeparture trainDeparture2 = new TrainDeparture(2, 2, "L2", "Trondheim", LocalTime.of(0, 5), station);
    station.addTrainDeparture(trainDeparture1);
    station.addTrainDeparture(trainDeparture2);

    System.out.println(trainDeparture1.getTrainNumber());
    System.out.println(trainDeparture2.getDestination());
    System.out.println(station.trainExists(1));
  }

  public static void main(String[] args) {
    TrainDispatchApp trainDispatchApp = new TrainDispatchApp();
    trainDispatchApp.initialize();
  }
}
