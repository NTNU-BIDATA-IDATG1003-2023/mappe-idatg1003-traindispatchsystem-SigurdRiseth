package edu.ntnu.stud.traindeparture;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.station.Station;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainDepartureTest {

  private Station station;

  /**
   * Test that track is set to the parameter when it is above 0.
   * @result Track is set to the parameter
   */

  @BeforeEach
  void setUp() {
    station = new Station();
    station.createTrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20));
    station.createTrainDeparture("2", 2, "L2", "Trondheim", LocalTime.of(5, 40));
  }

  @AfterEach
  void tearDown() {
    station = null;
  }

  @Test
  void setTrackValid() {
    station.getTrainDepartureByTrainNumber(2).setTrack("2");
    assertEquals(2, station.getTrainDepartureByTrainNumber(2).getTrack(), "Track should be 2");
  }

  /**
   * Test that track is set to -1 if it is under 1.
   * @result Track is set to -1
   */
  @Test
  void setTrackInvalid() {
    station.getTrainDepartureByTrainNumber(2).setTrack("-2");
    assertEquals(-1, station.getTrainDepartureByTrainNumber(2).getTrack(), "Track should be -1");
  }

  /**
   * Test that train number is set to the parameter when it is above 0.
   * @result Train number is set to the parameter
   */
  @Test
  void setTrainNumberValid() {
    station.getTrainDepartureByTrainNumber(2).setTrainNumber(3);
    assertEquals(3, station.getTrainDepartureByTrainNumber(2).getTrainNumber(), "Train number should be 2");
  }

  /**
   * Test that train number is set to -1 if it is under 1.
   * @result Train number is set to -1
   */
  @Test
  void setTrainNumberUnderOne() {
    station.getTrainDepartureByTrainNumber(2).setTrainNumber(-2);
    assertEquals(-2, station.getTrainDepartureByDestination("Trondheim").getTrainNumber(), "Train number should be -1");
  }

  /**
   * Test that train number is set to -1 if it already exists in Stations HashMap.
   * @result Train number is set to -1
   */
  @Test
  void setTrainNumberAlreadyExists() {
    station.getTrainDepartureByTrainNumber(2).setTrainNumber(1);
    assertEquals(1, station.getTrainDepartureByDestination("Trondheim").getTrainNumber(), "Train number should be -1");
  }

  /**
   * Test that line is set to the parameter when it is a String.
   * @result Line is set to the parameter
   */
  @Test
  void setLineValid() {
    station.getTrainDepartureByTrainNumber(2).setLine("L2");
    assertEquals("L2", station.getTrainDepartureByTrainNumber(2).getLine(), "Line should be L2");
  }

  /**
   * Test that line is set to null if null is passed as parameter.
   * @result Line is set to null
   */
  @Test
  void setLineNull() {
    station.getTrainDepartureByTrainNumber(2).setLine(null);
    assertNull(station.getTrainDepartureByTrainNumber(2).getLine(), "Line should be null");
  }

  /**
   * Test that destination is set to the parameter when it is a String.
   * @result Destination is set to the parameter
   */
  @Test
  void setDestinationValid() {
    station.getTrainDepartureByTrainNumber(2).setDestination("Bergen");
    assertEquals("Bergen", station.getTrainDepartureByTrainNumber(2).getDestination(), "Destination should be Bergen");
  }

  /**
   * Test that destination is set to "Invalid destination" if null is passed as parameter.
   * @result Destination is set to "Invalid destination"
   */
  @Test
  void setDestinationNull() {
    station.getTrainDepartureByTrainNumber(2).setDestination(null);
    assertEquals("Invalid destination", station.getTrainDepartureByTrainNumber(2).getDestination(), "Destination should be Invalid destination");
  }

  /**
   * Test that departure time is set to 05:40 when circumstances allow it.
   * @result Departure time is set to 05:40
   */
  @Test
  void setDepartureTimeValid() {
    station.getTrainDepartureByTrainNumber(2).setDepartureTime(LocalTime.of(5, 40));
    assertEquals(LocalTime.of(5, 40), station.getTrainDepartureByTrainNumber(2).getDepartureTime(), "Departure time should be 05:40");
  }

  /**
   * Test that departure time is unchanged if parameter is before current time
   * @result Departure time is unchanged
   */
  @Test
  void setDepartureTimeBeforeCurrentTime() {
    station.setClock(LocalTime.of(5, 0));
    station.getTrainDepartureByTrainNumber(1).setDepartureTime(LocalTime.of(4, 0));
    assertEquals(LocalTime.of(4, 0), station.getTrainDepartureByTrainNumber(1).getDepartureTime(), "Departure time should be unchanged. 05:20");
  }

  /**
   * Test that delay is set to 00:20 when circumstances allow it.
   * @result Delay is set to 00:20
   */
  @Test
  void setDelayValid() {
    station.getTrainDepartureByTrainNumber(1).setDelay(LocalTime.of(5, 0));
    assertEquals(LocalTime.of(5, 0), station.getTrainDepartureByTrainNumber(1).getDelay(), "Delay should be 00:20");
  }

  /**
   * Test that delay is set to 00:00 if null is passed as parameter
   * @result Delay is set to 00:00
   */
  @Test
  void setDelayNull() {
    station.getTrainDepartureByTrainNumber(1).setDelay(null);
    assertEquals(LocalTime.of(0,0), station.getTrainDepartureByTrainNumber(1).getDelay(), "Delay should be 00:00");
  }

}