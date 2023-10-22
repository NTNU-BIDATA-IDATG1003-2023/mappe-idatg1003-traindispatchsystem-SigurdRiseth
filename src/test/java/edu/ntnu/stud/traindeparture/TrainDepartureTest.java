package edu.ntnu.stud.traindeparture;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.station.Station;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class TrainDepartureTest {

  private Station station = new Station();
  private TrainDeparture trainDeparture1 = new TrainDeparture(1, 1, "L1", "Oslo", LocalTime.of(5, 20), station);


  @Test
  void setTrackValid() {
    trainDeparture1.setTrack(2);
    assertEquals(2, trainDeparture1.getTrack(), "Track should be 2");
  }

  @Test
  void setTrackInvalid() {
    trainDeparture1.setTrack(-2);
    assertEquals(-1, trainDeparture1.getTrack(), "Track should be -1");
  }


  @Test
  void setTrainNumberValid() {
    trainDeparture1.setTrainNumber(2, station);
    assertEquals(2, trainDeparture1.getTrainNumber(), "Train number should be 2");
  }

  @Test
  void setTrainNumberUnderOne() {
    trainDeparture1.setTrainNumber(-2, station);
    assertEquals(-1, trainDeparture1.getTrainNumber(), "Train number should be -1");
  }

  @Test
  void setTrainNumberAlreadyExists() {
    TrainDeparture trainDeparture2 = new TrainDeparture(2, 2, "L2", "Trondheim", LocalTime.of(5, 40), station);
    station.addTrainDeparture(trainDeparture2);
    trainDeparture1.setTrainNumber(2, station);
    assertEquals(-1, trainDeparture1.getTrainNumber(), "Train number should be -1");
  }

  @Test
  void setLineValid() {
    trainDeparture1.setLine("L2");
    assertEquals("L2", trainDeparture1.getLine(), "Line should be L2");
  }

  @Test
  void setLineNull() {
    trainDeparture1.setLine(null);
    assertEquals(null, trainDeparture1.getLine(), "Line should be null");
  }

  @Test
  void setDestinationValid() {
    trainDeparture1.setDestination("Trondheim");
    assertEquals("Trondheim", trainDeparture1.getDestination(), "Destination should be Trondheim");
  }

  @Test
  void setDestinationNull() {
    trainDeparture1.setDestination(null);
    assertEquals("Invalid destination", trainDeparture1.getDestination(), "Destination should be Invalid destination");
  }

  @Test
  void setDepartureTimeValid() {
    trainDeparture1.setDepartureTime(LocalTime.of(5, 40), station);
    assertEquals(LocalTime.of(5, 40), trainDeparture1.getDepartureTime(), "Departure time should be 05:40");
  }

  @Test
  void setDepartureTimeBeforeCurrentTime() {
    station.setClock(LocalTime.of(5, 0));
    trainDeparture1.setDepartureTime(LocalTime.of(4, 40), station);
    assertEquals(LocalTime.of(5, 20), trainDeparture1.getDepartureTime(), "Departure time should be unchanged. 05:20");
  }

  @Test
  void setDelayValid() {
    trainDeparture1.setDelay(LocalTime.of(0, 20), station);
    assertEquals(LocalTime.of(0, 20), trainDeparture1.getDelay(), "Delay should be 00:20");
  }

  @Test
  void setDelayNull() {
    trainDeparture1.setDelay(null, station);
    assertEquals(LocalTime.of(0,0), trainDeparture1.getDelay(), "Delay should be 00:00");
  }

  @Test
  void setDelayOverMidnight() {
    trainDeparture1.setDepartureTime(LocalTime.of(23, 0), station);
    trainDeparture1.setDelay(LocalTime.of(1, 20), station);
    assertEquals(false, station.trainExists(trainDeparture1.getTrainNumber()), "Train should be removed from station");
  }

}