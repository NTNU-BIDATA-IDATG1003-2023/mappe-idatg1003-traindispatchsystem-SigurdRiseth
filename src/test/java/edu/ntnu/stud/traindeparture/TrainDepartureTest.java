package edu.ntnu.stud.traindeparture;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.station.Station;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class TrainDepartureTest {

  private Station station = new Station();
  private TrainDeparture trainDeparture1 = new TrainDeparture(1, 1, "L1", "Oslo", LocalTime.of(5, 20), station);

  @Test
  void setTrainNumberValid() {
    trainDeparture1.setTrainNumber(2, station);
    assertEquals(2, trainDeparture1.getTrainNumber(), "Train number should be 2");
  }

  @Test
  void setTrainNumberInvalid() {
    trainDeparture1.setTrainNumber(1, station);
    assertEquals(-1, trainDeparture1.getTrainNumber(), "Train number should be -1");
  }

}