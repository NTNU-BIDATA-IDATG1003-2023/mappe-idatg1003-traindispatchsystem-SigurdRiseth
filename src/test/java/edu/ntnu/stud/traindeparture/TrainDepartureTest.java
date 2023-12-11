package edu.ntnu.stud.traindeparture;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test class for TrainDeparture.
 *
 * <p>
 * Tests all public methods in TrainDeparture.
 * Uses both negative and positive tests.
 * </p>
 *
 * @see TrainDeparture
 * @since 14.10.2023
 * @version 0.0.1
 * @author Sigurd Riseth
 */
class TrainDepartureTest {

  private TrainDeparture trainDeparture1;

  /**
   * Test that track is set to the parameter when it is above 0.
   *
   * @result Track is set to the parameter
   */

  @BeforeEach
  void setUp() {
    trainDeparture1 = new TrainDeparture("1", 1, "L1", "Oslo", LocalTime.of(5, 20));
  }

  @AfterEach
  void tearDown() {
    trainDeparture1 = null;
  }

  /**
   * Negative test for setTrainNumber method.
   *
   * <p>
   *   Attempts to set the train number to a negative number.
   *   Expected result is that the train number is -1.
   * </p>
   *
   * @result Train number is -1
   */
  @Test
  void setTrainNumberNegative() {
    trainDeparture1 = new TrainDeparture("1", -5, "L1", "Oslo", LocalTime.of(5, 20));
    assertEquals(-1, trainDeparture1.getTrainNumber(), "Expected train number to be -1");
  }


  /**
   * Positive test for setTrack method.
   *
   * @result Track is set to 2
   */
  @Test
  void setTrackValid() {
    trainDeparture1.setTrack("2");
    assertEquals(2, trainDeparture1.getTrack(), "Expected track to be 2");
  }

  /**
   * Test that track is set to -1 if it is under 1.
   *
   * @result Track is set to -1
   */
  @Test
  void setTrackNegative() {
    trainDeparture1.setTrack("-2");
    assertEquals(-1, trainDeparture1.getTrack(), "Expected track to be -1");
  }

  /**
   * Test that track is set to -1 if it is not a number.
   *
   * @result Track is set to -1
   */
  @Test
  void setTrackString() {
    trainDeparture1.setTrack("a");
    assertEquals(-1, trainDeparture1.getTrack(), "Expected track to be -1");
  }

  /**
   * Test that delay is set to 00:20 when circumstances allow it.
   *
   * @result Delay is set to 00:20
   */
  @Test
  void setDelayValid() {
    trainDeparture1.setDelay(LocalTime.of(0, 20));
    assertEquals(LocalTime.of(0, 20), trainDeparture1.getDelay(), "Expected delay to be 00:20");
  }

  /**
   * Test that delay is set to 00:00 if null is passed as parameter
   *
   * @result Delay is set to 00:00
   */
  @Test
  void setDelayNull() {
    trainDeparture1.setDelay(null);
    assertEquals(LocalTime.of(0, 0), trainDeparture1.getDelay(), "Expected delay to be 00:00");
  }

  /**
   * Test of getDepartureTimeWithDelay method.
   *
   * @result getDepartureTimeWithDelay returns the correct departure time with delay
   */
  @Test
  void getDepartureTimeWithDelay() {
    trainDeparture1.setDelay(LocalTime.of(0, 20));
    assertEquals(LocalTime.of(5, 40), trainDeparture1.getDepartureTimeWithDelay(),
        "Expected departure time with delay to be 05:40");
  }

}