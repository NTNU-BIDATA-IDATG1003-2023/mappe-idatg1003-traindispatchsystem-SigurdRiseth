package edu.ntnu.stud.station;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationTest {

  private Station station;

  @BeforeEach
  void setUp() {
    //ARRANGE
    station = new Station();
    station.createTrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20));
    station.createTrainDeparture("2", 2, "L2", "Trondheim", LocalTime.of(5, 40));
  }

  @AfterEach
  void tearDown() {
    station = null;
  }

  @Test
  void setClockValidTime() {
    // KAN OGSÅ GJØRE ARRANGE HER
    //ACT
    //ASSERT
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
    //assertEquals(expected, actual, "Should return \"Time cannot be set to a time before the current time.\"");
    assertEquals(LocalTime.of(14, 0), station.getClock(), "Should be 14:00");
  }

  @Test
  void getClock() {
    LocalTime expected = LocalTime.of(0, 0);
    LocalTime actual = station.getClock();
    assertEquals(expected, actual, "GetClock has failure");
  }

  @Test // Skrevet med chatgpt. forbedret siste del fra flere asserequals til en assertall
  void createTrainDeparture() {
    station.createTrainDeparture("3", 3, "L3", "Bergen", LocalTime.of(12, 0));
    TrainDeparture actual = station.getTrainDepartureByTrainNumber(3);

    assertAll("Train Departure Attributes",
        () -> assertEquals(3, actual.getTrainNumber(), "Train Number should be equal"),
        () -> assertEquals(LocalTime.of(12,0), actual.getDepartureTime(), "Departure Time should be equal"),
        () -> assertEquals(3, actual.getTrack(), "Track should be equal"),
        () -> assertEquals("Bergen", actual.getDestination(), "Destination should be equal"),
        () -> assertEquals("L3", actual.getLine(), "Line should be equal"),
        () -> assertEquals(LocalTime.of(0,0), actual.getDelay(), "Delay should be equal")
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
    List<TrainDeparture> expectedDepartures = Arrays.asList(
        new TrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20)),
        new TrainDeparture("2", 2, "L2", "Trondheim", LocalTime.of(5, 40))
    );
    Iterator<TrainDeparture> iterator = station.getTrainDeparturesSorted();

    int count = 0;
    while (iterator.hasNext()) {
      assertTrue(count < expectedDepartures.size(), "Iterator produced more elements than expected");
      TrainDeparture actualDeparture = iterator.next();
      TrainDeparture expectedDeparture = expectedDepartures.get(count);
      assertEquals(expectedDeparture.getTrainNumber(), actualDeparture.getTrainNumber(), "Element at index " + count + " is not as expected");
      count++;
    }

    assertEquals(expectedDepartures.size(), count, "Iterator produced fewer elements than expected");

  }

  @Test
  void getTrainDeparturesSortedSomeBeforeClock() {
    station.setClock(LocalTime.of(5, 21));

    List<TrainDeparture> expectedDepartures = Arrays.asList(
        new TrainDeparture("2", 2, "L2", "Trondheim", LocalTime.of(5, 40))
    );
    Iterator<TrainDeparture> iterator = station.getTrainDeparturesSorted();

    int count = 0;
    while (iterator.hasNext()) {
      assertTrue(count < expectedDepartures.size(), "Iterator produced more elements than expected");
      TrainDeparture actualDeparture = iterator.next();
      TrainDeparture expectedDeparture = expectedDepartures.get(count);
      assertEquals(expectedDeparture.getTrainNumber(), actualDeparture.getTrainNumber(), "Element at index " + count + " is not as expected");
      count++;
    }

    assertEquals(expectedDepartures.size(), count, "Iterator produced fewer elements than expected");

  }

  @Test
  void trainExistsTrue() {
    assertTrue(station.trainExists(1), "Should be true");
  }

  @Test
  void trainExistsFalse() {
    assertFalse(station.trainExists(3), "Should be false");
  }

  @Test
  void changeTrackByTrainNumberInUse() {
    assertEquals("Track changed.", station.changeTrackByTrainNumber(1, "3"), "Wrong string returned. Should be \"Track changed.\"");
    assertEquals(3, station.getTrainDepartureByTrainNumber(1).getTrack(), "Track changed to wrong value.");
  }

  @Test
  void changeTrackByTrainNumberUnUsed() {
    assertEquals("Train does not exist.", station.changeTrackByTrainNumber(3, "3"), "Should be \"Train does not exist.\"");
  }

  @Test
  void changeDelayByTrainNumberInUse() {
    assertEquals("Delay changed.", station.changeDelayByTrainNumber(1, LocalTime.of(12, 0)), "Should be \"Delay changed.\"");
    assertEquals(LocalTime.of(12, 0), station.getTrainDepartureByTrainNumber(1).getDelay(), "Should be 12:00");
  }

  @Test
  void changeDelayByTrainNumberUnUsed() {
    assertEquals("Train does not exist. Please try again.", station.changeDelayByTrainNumber(3, LocalTime.of(12, 0)), "Should be \"Train does not exist. Please try again.\"");
  }

  @Test
  void getTrainDepartureByTrainNumberInUse() {
    TrainDeparture actual = station.getTrainDepartureByTrainNumber(1);
    assertAll("Train Departure Attributes",
        () -> assertEquals(1, actual.getTrainNumber(), "Train Number should be equal"),
        () -> assertEquals(LocalTime.of(5, 20), actual.getDepartureTime(), "Departure Time should be equal"),
        () -> assertEquals(1, actual.getTrack(), "Track should be equal"),
        () -> assertEquals("Oslo", actual.getDestination(), "Destination should be equal"),
        () -> assertEquals("L1", actual.getLine(), "Line should be equal"),
        () -> assertEquals(LocalTime.of(0,0), actual.getDelay(), "Delay should be equal")
    );
  }

  @Test
  void getTrainDepartureByTrainNumberNotInUse() {
    assertEquals(null, station.getTrainDepartureByTrainNumber(3), "Should be null");
  }

  @Test
  void getTrainDepartureByDestinationInUse() {
    TrainDeparture actual = station.getTrainDepartureByDestination("Oslo");
    assertAll("Train Departure Attributes",
        () -> assertEquals(1, actual.getTrainNumber(), "Train Number should be equal"),
        () -> assertEquals(LocalTime.of(5, 20), actual.getDepartureTime(), "Departure Time should be equal"),
        () -> assertEquals(1, actual.getTrack(), "Track should be equal"),
        () -> assertEquals("Oslo", actual.getDestination(), "Destination should be equal"),
        () -> assertEquals("L1", actual.getLine(), "Line should be equal"),
        () -> assertEquals(LocalTime.of(0,0), actual.getDelay(), "Delay should be equal")
    );
  }

  @Test
  void getTrainDepartureByDestinationNotInUse() {
    assertEquals(null, station.getTrainDepartureByDestination("Bergen"), "Should be null");
  }

  @Test
  void removeTrainDepartureByTrainNumber() {
    station.removeTrainDepartureByTrainNumber(1);
    assertEquals(null, station.getTrainDepartureByTrainNumber(1), "Should be null");
  }


}