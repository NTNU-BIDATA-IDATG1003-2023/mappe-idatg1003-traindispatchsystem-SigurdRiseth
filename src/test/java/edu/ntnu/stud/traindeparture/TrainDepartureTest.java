package edu.ntnu.stud.traindeparture;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.station.Station;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class TrainDepartureTest {

  private Station station = new Station();
  private TrainDeparture trainDeparture1 = new TrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20), station);

  /**
   * Test that track is set to the parameter when it is above 0.
   * @result Track is set to the parameter
   */
  @Test
  void setTrackValid() {
    trainDeparture1.setTrack("2");
    assertEquals(2, trainDeparture1.getTrack(), "Track should be 2");
  }

  /**
   * Test that track is set to -1 if it is under 1.
   * @result Track is set to -1
   */
  @Test
  void setTrackInvalid() {
    trainDeparture1.setTrack("-2");
    assertEquals(-1, trainDeparture1.getTrack(), "Track should be -1");
  }

  /**
   * Test that train number is set to the parameter when it is above 0.
   * @result Train number is set to the parameter
   */
  @Test
  void setTrainNumberValid() {
    trainDeparture1.setTrainNumber(2);
    assertEquals(2, trainDeparture1.getTrainNumber(), "Train number should be 2");
  }

  /**
   * Test that train number is set to -1 if it is under 1.
   * @result Train number is set to -1
   */
  @Test
  void setTrainNumberUnderOne() {
    trainDeparture1.setTrainNumber(-2);
    assertEquals(-1, trainDeparture1.getTrainNumber(), "Train number should be -1");
  }

  /**
   * Test that train number is set to -1 if it already exists in Stations HashMap.
   * @result Train number is set to -1
   */
  @Test
  void setTrainNumberAlreadyExists() {
    station.createTrainDeparture("2", 2, "L2", "Trondheim", LocalTime.of(5, 40), station);
    trainDeparture1.setTrainNumber(2);
    assertEquals(-1, trainDeparture1.getTrainNumber(), "Train number should be -1");
  }

  /**
   * Test that line is set to the parameter when it is a String.
   * @result Line is set to the parameter
   */
  @Test
  void setLineValid() {
    trainDeparture1.setLine("L2");
    assertEquals("L2", trainDeparture1.getLine(), "Line should be L2");
  }

  /**
   * Test that line is set to null if null is passed as parameter.
   * @result Line is set to null
   */
  @Test
  void setLineNull() {
    trainDeparture1.setLine(null);
    assertEquals(null, trainDeparture1.getLine(), "Line should be null");
  }

  /**
   * Test that destination is set to the parameter when it is a String.
   * @result Destination is set to the parameter
   */
  @Test
  void setDestinationValid() {
    trainDeparture1.setDestination("Trondheim");
    assertEquals("Trondheim", trainDeparture1.getDestination(), "Destination should be Trondheim");
  }

  /**
   * Test that destination is set to "Invalid destination" if null is passed as parameter.
   * @result Destination is set to "Invalid destination"
   */
  @Test
  void setDestinationNull() {
    trainDeparture1.setDestination(null);
    assertEquals("Invalid destination", trainDeparture1.getDestination(), "Destination should be Invalid destination");
  }

  /**
   * Test that departure time is set to 05:40 when circumstances allow it.
   * @result Departure time is set to 05:40
   */
  @Test
  void setDepartureTimeValid() {
    trainDeparture1.setDepartureTime(LocalTime.of(5, 40));
    assertEquals(LocalTime.of(5, 40), trainDeparture1.getDepartureTime(), "Departure time should be 05:40");
  }

  /**
   * Test that departure time is unchanged if parameter is before current time
   * @result Departure time is unchanged
   */
  @Test
  void setDepartureTimeBeforeCurrentTime() {
    station.setClock(LocalTime.of(5, 0));
    trainDeparture1.setDepartureTime(LocalTime.of(4, 40));
    assertEquals(LocalTime.of(5, 20), trainDeparture1.getDepartureTime(), "Departure time should be unchanged. 05:20");
  }

  /**
   * Test that delay is set to 00:20 when circumstances allow it.
   * @result Delay is set to 00:20
   */
  @Test
  void setDelayValid() {
    trainDeparture1.setDelay(LocalTime.of(0, 20));
    assertEquals(LocalTime.of(0, 20), trainDeparture1.getDelay(), "Delay should be 00:20");
  }

  /**
   * Test that delay is set to 00:00 if null is passed as parameter
   * @result Delay is set to 00:00
   */
  @Test
  void setDelayNull() {
    trainDeparture1.setDelay(null);
    assertEquals(LocalTime.of(0,0), trainDeparture1.getDelay(), "Delay should be 00:00");
  }

  /**
   * Test that train is removed from station if delayed past midnight
   * @result Train is removed from station.
   */
  @Test
  void setDelayOverMidnight() {
    trainDeparture1.setDepartureTime(LocalTime.of(23, 0));
    trainDeparture1.setDelay(LocalTime.of(1, 20));
    assertEquals(false, station.trainExists(trainDeparture1.getTrainNumber()), "Train should be removed from station");
  }

}