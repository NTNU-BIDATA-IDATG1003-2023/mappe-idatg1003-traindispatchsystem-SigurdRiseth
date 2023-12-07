package edu.ntnu.stud.station;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Iterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationTest {

  private Station station; // ARRANGE ACT ASSERT

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

  /**
   * Positive test for setClock method.
   * <p>
   * Attempts to set the clock to a time after the current time. Expected result is that the clock
   * is set to the given time.
   * </p>
   *
   * @result Clock is set to the given time
   */
  @Test
  void setClockValidTime() {
    station.setClock(LocalTime.of(5, 30));
    assertEquals(LocalTime.of(5, 30), station.getClock(), "Expected time to be 05:30");
  }

  /**
   * Negative test for setClock method.
   * <p>
   * Attempts to set the clock to a time before the current time. Expected result is that the clock
   * is not set to the given time.
   * </p>
   *
   * @result Clock is not set to the given time
   */
  @Test
  void setClockInvalidTime() {
    station.setClock(LocalTime.of(5, 10)); // Set up for the test
    station.setClock(LocalTime.of(3, 0));
    assertEquals(LocalTime.of(5, 10), station.getClock(), "Expected time to be 05:10");
  }

  /**
   * Positive test for getClock method.
   * <p>
   * Attempts to get the clock. Expected result is that the clock is returned.
   * </p>
   *
   * @result Clock is returned
   */
  @Test
  void getClock() {
    assertEquals(LocalTime.of(0, 0), station.getClock(), "Expected time to be 00:00");
  }

  /**
   * Test for getAmountOfTrainDepartures method.
   * <p>
   * Compares the size of the HashMap to the expected size.
   * </p>
   *
   * @result Size of HashMap is 2
   */
  @Test
  void getAmountOfTrainDeparturesToDepart() {
    assertEquals(2, station.getAmountOfTrainDeparturesToDepart(), "Expected size to be 2");
  }

  /**
   * Positive test for createTrainDeparture method.
   * <p>
   * Attempts to create a new TrainDeparture. Checks the following:
   *   <ul>
   *     <li>Size of HashMap is updated</li>
   *     <li>TrainDeparture is added to the HashMap, with correct values.</li>
   *   </ul>
   * </p>
   *
   * @result TrainDeparture is added to the HashMap with correct values
   */
  @Test
  void createTrainDeparture() {
    station.createTrainDeparture("3", 3, "L3", "Bergen", LocalTime.of(5, 50));

    assertEquals(3, station.getAmountOfTrainDeparturesToDepart(),
        "Expected size of HashMap to be 3");
    assertAll("Expected TrainDeparture to have correct values for its fields",
        () -> assertEquals(3, station.getTrainDepartureByTrainNumber(3).getTrainNumber(),
            "Expected track to be 3"),
        () -> assertEquals(3, station.getTrainDepartureByTrainNumber(3).getTrack(),
            "Expected train number to be 3"),
        () -> assertEquals("L3", station.getTrainDepartureByTrainNumber(3).getLine(),
            "Expected line to be L3"),
        () -> assertEquals("Bergen", station.getTrainDepartureByTrainNumber(3).getDestination(),
            "Expected destination to be Bergen"),
        () -> assertEquals(LocalTime.of(5, 50),
            station.getTrainDepartureByTrainNumber(3).getDepartureTime(),
            "Expected departure time to be 05:50")
    );
  }

  /**
   * Negative test for createTrainDeparture method.
   * <p>
   * Attempts to create a new TrainDeparture with a train number that already exists. Expected
   * result is that the TrainDeparture is not added to the HashMap.
   * </p>
   *
   * @result TrainDeparture is not added to the HashMap
   */
  @Test
  void createTrainDepartureTrainNumberExists() {
    station.createTrainDeparture("3", 1, "L3", "Bergen", LocalTime.of(5, 50));

    assertEquals(2, station.getAmountOfTrainDeparturesToDepart(),
        "Expected size of HashMap to be 2");
  }

  /**
   * Positive test for getTrainDeparturesSorted method.
   * <p>
   * Attempts to get an iterator of all TrainDepartures yet to depart. Checks the following:
   *   <ul>
   *     <li>TrainDepartures are sorted by departure time</li>
   *     <li>TrainDepartures that have already departed are filtered out</li>
   *   </ul>
   * </p>
   *
   * @result Iterator of all TrainDepartures yet to depart is returned, sorted by departure time.
   */
  @Test
  void getTrainDeparturesSorted() {
    station.createTrainDeparture("3", 3, "L3", "Bergen", LocalTime.of(5, 50));
    station.setClock(LocalTime.of(5, 30));
    Iterator<TrainDeparture> iterator = station.getTrainDeparturesSorted();

    assertAll("Expected iterator to be sorted by departure time and filtered by current time",
        () -> assertEquals(2, iterator.next().getTrainNumber(), "Expected train number to be 1"),
        () -> assertEquals(3, iterator.next().getTrainNumber(), "Expected train number to be 2")
    );
  }

  /**
   * Test for trainExists method.
   * <p>
   * Tests that the method returns true if the train exists and false if it does not.
   * </p>
   *
   * @result Returns true if train exists, false if not
   */
  @Test
  void trainExists() {
    assertTrue(station.trainExists(1), "Expected train to exist");
    assertFalse(station.trainExists(3), "Expected train to not exist");
  }

  /**
   * Test for changeTrackByTrainNumber method.
   * <p>
   * Attempts to change the track of a train with the given train number. Checks both positive and
   * negative cases.
   * </p>
   *
   * @result Track is changed to correct value
   */
  @Test
  void changeTrackByTrainNumber() {
    station.changeTrackByTrainNumber(1, "3");
    assertEquals(3, station.getTrainDepartureByTrainNumber(1).getTrack(), "Expected track to be 3");
    station.changeTrackByTrainNumber(2, "none");
    assertEquals(-1, station.getTrainDepartureByTrainNumber(2).getTrack(),
        "Expected track to be -1");
  }

  /**
   * Positive test for changeDelayByTrainNumber method.
   * <p>
   * Attempts to change the delay of a train with the given train number. TrainDeparture is not
   * delayed over midnight.
   * </p>
   *
   * @result Delay is changed to correct value
   */
  @Test
  void changeDelayByTrainNumberPositive() {
    station.changeDelayByTrainNumber(1, LocalTime.of(0, 20));
    assertEquals(LocalTime.of(0, 20), station.getTrainDepartureByTrainNumber(1).getDelay(),
        "Expected delay to be 00:20");
  }

  /**
   * Negative test for changeDelayByTrainNumber method.
   * <p>
   * Attempts to change the delay of a train with the given train number. TrainDeparture is delayed
   * over midnight.
   * </p>
   *
   * @result TrainDeparture is removed
   */
  @Test
  void changeDelayByTrainNumberNegative() {
    station.changeDelayByTrainNumber(1, LocalTime.of(23, 0));
    assertNull(station.getTrainDepartureByTrainNumber(1), "Expected train to be removed");
  }

  /**
   * Positive for getTrainDepartureByDestination method.
   * <p>
   * Attempts to get a TrainDeparture with the given destination. Expected result is that the
   * TrainDeparture is returned.
   * </p>
   *
   * @result Correct TrainDeparture is returned
   */
  @Test
  void getTrainDepartureByDestinationPositive() {
    assertEquals(1, station.getTrainDepartureByDestination("Oslo").getTrainNumber(),
        "Expected train number to be 1");
  }

  /**
   * Negative for getTrainDepartureByDestination method.
   * <p>
   * Attempts to get a TrainDeparture with the given destination. Expected result is that null is
   * returned.
   * </p>
   *
   * @result Null is returned
   */
  @Test
  void getTrainDepartureByDestinationNegative() {
    assertNull(station.getTrainDepartureByDestination("Bergen"), "Expected train to not exist");
  }

  /**
   * Test for removeTrainDepartureByTrainNumber method.
   * <p>
   * Attempts to remove a TrainDeparture with the given train number. Checks if the train is removed
   * from the HashMap.
   * </p>
   *
   * @result TrainDeparture is removed from HashMap
   */
  @Test
  void removeTrainDepartureByTrainNumber() {
    station.removeTrainDepartureByTrainNumber(1);
    assertFalse(station.trainExists(1), "Expected train to not exist");
    assertEquals(1, station.getAmountOfTrainDeparturesToDepart(),
        "Expected size of HashMap to be 1");
  }


}