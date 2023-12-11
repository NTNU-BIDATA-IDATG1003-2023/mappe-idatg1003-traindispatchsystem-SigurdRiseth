package edu.ntnu.stud.traindeparture;

import java.time.LocalTime;
import java.util.Objects;

/**
 * The TrainDeparture class is the blueprint for the train departures in the Train Dispatch System.
 *
 * <p>
 * Stores information about the train departure,
 * and contains methods for setting and getting these values.
 * The TrainDeparture class uses LocalTime to store the departure time and the delay.
 * The fields it stores are:
 * <ul>
 *   <li>Train number</li>
 *   <li>Line</li>
 *   <li>Destination</li>
 *   <li>Track</li>
 *   <li>Departure time</li>
 *   <li>Delay</li>
 * </ul>
 * </p>
 *
 * @author Sigurd Riseth
 * @version 0.0.1
 * @see LocalTime
 * @since 14.10.2023
 */
public class TrainDeparture {

  private int trainNumber;
  private String line;
  private String destination;
  private int track;
  private LocalTime departureTime;
  private LocalTime delay;

  /**
   * Constructor that sets all parameters of the class to the given parameter except delay which is
   * default 00:00.
   *
   * @param track         track the train will depart from
   * @param trainNumber   unique train ID
   * @param line          the line the train is driving
   * @param destination   the ends destination of the train
   * @param departureTime the time the train is set to depart
   */
  public TrainDeparture(String track, int trainNumber, String line, String destination,
      LocalTime departureTime) {
    this.setTrack(track);
    this.setTrainNumber(trainNumber);
    this.setLine(line);
    this.setDestination(destination);
    this.setDepartureTime(departureTime);
    this.setDelay(LocalTime.of(0, 0));
  }

  /**
   * Returns the track number as an integer.
   *
   * @return An integer representing the track number.
   */

  public int getTrack() {
    return track;
  }

  /**
   * Sets the track. If the track is under 1 or a String the value is set to -1.
   * <p>A track value of -1 represents that no track has been assigned.</p>
   */
  public void setTrack(String track) {
    try {
      if (Integer.parseInt(track) > 0) {
        this.track = Integer.parseInt(track);
      } else {
        this.track = -1;
      }
    } catch (Exception e) {
      this.track = -1;
    }
  }

  /**
   * Returns the train number as an integer.
   *
   * @return An integer representing the train number.
   */
  public int getTrainNumber() {
    return trainNumber;
  }

  /**
   * Sets the train number. If the train number is under 1 the train number is set to -1.
   *
   * @param trainNumber train number to be set
   */
  private void setTrainNumber(int trainNumber) {
    if (trainNumber > 0) {
      this.trainNumber = trainNumber;
    } else {
      this.trainNumber = -1;
    }
  }

  /**
   * Returns the line as a String.
   *
   * @return A String representing the line.
   */
  public String getLine() {
    return line;
  }

  /**
   * Sets the line. If the line is null the line is set to "Invalid line"
   *
   * @param line line to be set
   */
  private void setLine(String line) {
    this.line = Objects.requireNonNullElse(line, "Invalid line");
  }

  /**
   * Returns the destination as a String.
   *
   * @return A String representing the destination.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Sets the destination. If the destination is null the destination is set to "Invalid
   * destination"
   *
   * @param destination destination to be set
   */
  private void setDestination(String destination) {
    this.destination = Objects.requireNonNullElse(destination, "Invalid destination");
  }

  /**
   * Returns the departure time as a LocalTime object.
   *
   * @return The LocalTime object representing the departure time.
   */
  public LocalTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Sets the departure time.
   *
   * @param departureTime departure time to be set
   */
  private void setDepartureTime(LocalTime departureTime) {
    this.departureTime = departureTime;
  }

  /**
   * Returns the departure time with the delay added to it.
   *
   * @return The LocalTime object representing the departure time with the delay added to it.
   */
  public LocalTime getDepartureTimeWithDelay() {
    return departureTime.plusHours(delay.getHour()).plusMinutes(delay.getMinute());
  }

  /**
   * Returns the delay represented as a LocalTime.
   *
   * @return The LocalTime object representing the delay.
   */
  public LocalTime getDelay() {
    return delay;
  }

  /**
   * Sets the delay. If the delay is null the delay is set to 00:00.
   *
   * @param delay delay to be set
   */
  public void setDelay(LocalTime delay) {
    if (delay != null) {
      this.delay = delay;
    } else {
      this.delay = LocalTime.of(0, 0);
    }
  }
}
