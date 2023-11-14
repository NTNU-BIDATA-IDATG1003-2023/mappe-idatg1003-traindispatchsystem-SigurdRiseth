package edu.ntnu.stud.station;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.TrainDispatchApp;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationTest {

  private Station station;

  @BeforeEach
  void setUp() {
    station = new Station();
    station.createTrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20));
    station.createTrainDeparture("2", 2, "L2", "Trondheim", LocalTime.of(5, 40));

  }
  @Test
  void setClockValidTime() {
    String expected = "Time set to 12:00";
    String actual = station.setClock(LocalTime.of(12, 0));
    assertEquals(expected, actual, "Should return \"Time set to 12:00\"");
    assertEquals(LocalTime.of(12, 0), station.getClock(), "Should be 12:00");
  }

  @Test
  void setClockToTimeBeforeCurrentTime() {
    station.setClock(LocalTime.of(14, 0));
    String expected = "Time cannot be set to a time before the current time.";
    String actual = station.setClock(LocalTime.of(12, 0));
    assertEquals(expected, actual, "Should return \"Time cannot be set to a time before the current time.\"");
    assertEquals(LocalTime.of(14, 0), station.getClock(), "Should be 14:00");
  }

  @Test
  void getClock() {
    LocalTime expected = LocalTime.of(0, 0);
    LocalTime actual = station.getClock();
    assertEquals(expected, actual, "Should be 00:00");
  }

  @Test // Skrevet med chatgpt. forbedret siste del fra flere asserequals til en assertall
  void createTrainDeparture() {
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
  /* private method
  @Test
  void addTrainDeparture() {
    Station station = new Station();
    TrainDeparture input = new TrainDeparture("3", 1, "L3", "Bergen", LocalTime.of(12, 0), station);
    station.addTrainDeparture(input);

    assertEquals(input, station.getTrainDepartureByTrainNumber(1), "Should be equal");

  }*/

  @Test
  void getTrainDeparturesSortedAllAfterClock() {
    assertEquals(2, station.getTrainDeparturesSorted().size(), "Should be 2 in size");
    assertEquals(1, station.getTrainDeparturesSorted().get(0).getTrainNumber(), "Should be train number 1");
  }

  @Test
  void getTrainDeparturesSortedSomeBeforeClock() {
    station.setClock(LocalTime.of(5, 21));
    assertEquals(1, station.getTrainDeparturesSorted().size(), "Should be 2 in size");
    assertEquals(2, station.getTrainDeparturesSorted().get(0).getTrainNumber(), "Should be train number 2");
  }



}