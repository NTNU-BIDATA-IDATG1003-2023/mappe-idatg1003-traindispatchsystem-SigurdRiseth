package edu.ntnu.stud.station;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class StationTest {

  @Test
  public void setClockValidTime() {
    Station station = new Station();
    String expected = "Time set to 12:00";
    String actual = station.setClock(LocalTime.of(12, 0));
    assertEquals(expected, actual, "Should return \"Time set to 12:00\"");
    assertEquals(LocalTime.of(12, 0), station.getClock(), "Should be 12:00");
  }

  @Test
  public void setClockToTimeBeforeCurrentTime() {
    Station station = new Station();
    station.setClock(LocalTime.of(14, 0));
    String expected = "Time cannot be set to a time before the current time.";
    String actual = station.setClock(LocalTime.of(12, 0));
    assertEquals(expected, actual, "Should return \"Time cannot be set to a time before the current time.\"");
    assertEquals(LocalTime.of(14, 0), station.getClock(), "Should be 14:00");
  }

  @Test
  public void getClock() {
    Station station = new Station();
    LocalTime expected = LocalTime.of(0, 0);
    LocalTime actual = station.getClock();
    assertEquals(expected, actual, "Should be 00:00");
  }

   /* forbedret med chatgpt
     @Test
     public void createTrainDeparture() {
      Station station = new Station();
      TrainDeparture expected = new TrainDeparture("3", 1, "L3", "Bergen", LocalTime.of(12, 0));
      station.createTrainDeparture("3", 1, "L3", "Bergen", LocalTime.of(12, 0));

      assertEquals(station.getTrainDepartureByTrainNumber(1).getTrainNumber(), expected.getTrainNumber(), "Should be equal");
      assertEquals(station.getTrainDepartureByTrainNumber(1).getDepartureTime(), expected.getDepartureTime(), "Should be equal");
      assertEquals(station.getTrainDepartureByTrainNumber(1).getTrack(), expected.getTrack(), "Should be equal");
      assertEquals(station.getTrainDepartureByTrainNumber(1).getDestination(), expected.getDestination(), "Should be equal");
      assertEquals(station.getTrainDepartureByTrainNumber(1).getLine(), expected.getLine(), "Should be equal");
      assertEquals(station.getTrainDepartureByTrainNumber(1).getDelay(), expected.getDelay(), "Should be equal");
  }
    */
  @Test
  public void createTrainDeparture() {
    Station station = new Station();
    TrainDeparture expected = new TrainDeparture("3", 1, "L3", "Bergen", LocalTime.of(12, 0), station);
    station.createTrainDeparture("3", 1, "L3", "Bergen", LocalTime.of(12, 0));
    TrainDeparture actual = station.getTrainDepartureByTrainNumber(1);

    assertAll("Train Departure Attributes",
        () -> assertEquals(expected.getTrainNumber(), actual.getTrainNumber(), "Train Number should be equal"),
        () -> assertEquals(expected.getDepartureTime(), actual.getDepartureTime(), "Departure Time should be equal"),
        () -> assertEquals(expected.getTrack(), actual.getTrack(), "Track should be equal"),
        () -> assertEquals(expected.getDestination(), actual.getDestination(), "Destination should be equal"),
        () -> assertEquals(expected.getLine(), actual.getLine(), "Line should be equal"),
        () -> assertEquals(expected.getDelay(), actual.getDelay(), "Delay should be equal")
    );
  }

  @Test
  public void addTrainDeparture() {
    Station station = new Station();

  }



}