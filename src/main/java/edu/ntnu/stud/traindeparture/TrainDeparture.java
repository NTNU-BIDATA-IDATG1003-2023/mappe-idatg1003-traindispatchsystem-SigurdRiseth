package edu.ntnu.stud.traindeparture;

import java.time.LocalTime;

/**
 * Class for the train departures.
 * <p>Will store information such as train number, line, departure time, delay,
 * destination and what track it will depart from.</p>
 *
 * @author Sigurd Riseth
 * @version 0.0.1
 * @since 14.10.2023
 */
public class TrainDeparture {

  private int track;
  private int trainNumber;
  private String line;
  private String destination;
  private LocalTime departureTime; // use a Clock class to store departureTime and delay.
  private LocalTime delay;

  /**
   * Constructor that sets all parameters of the class except delay.
   *
   * @param track         track the train will depart from
   * @param trainNumber   unique train ID
   * @param line          the line the train is driving
   * @param destination   the ends destination of the train
   * @param departureTime the time the train is set to depart
   */
  public TrainDeparture(int track, int trainNumber, String line, String destination,
      LocalTime departureTime) {
    this.setTrack(track);
    this.setTrainNumber(trainNumber);
    this.setLine(line);
    this.setDestination(destination);
    this.setDepartureTime(departureTime);
    this.delay = LocalTime.of(0, 0);
  }

  /**
   * Sets the track.
   */
  public void setTrack(int track) {
    this.track = track;
  }

  /**
   * Sets the train number.
   */
  public void setTrainNumber(int trainNumber) {
    if (trainNumber > 0) { // må gjøre slik at tognummer ikke kan dupliseres.
      this.trainNumber = trainNumber;
    } else {
      this.trainNumber = -1;
    }
  }

  /**
   * Sets the line.
   */
  public void setLine(String line) {
    this.line = line;
  }

  /**
   * Sets the destination.
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Sets the departure time.
   */
  public void setDepartureTime(LocalTime departureTime) {
    this.departureTime = departureTime;
  }

  /**
   * Retrieves the track number as an integer.
   *
   * @return An integer representing the track number.
   */
  public int getTrack() {
    return track;
  }


  /**
   * Retrieves the train number as an integer.
   *
   * @return An integer representing the train number.
   */
  public int getTrainNumber() {
    return trainNumber;
  }


  /**
   * Retrieves the line information as a String.
   *
   * @return A String containing information about the line.
   */
  public String getLine() {
    return line;
  }


  /**
   * Retrieves the destination of this entity as a String.
   *
   * @return A String representing the destination.
   */
  public String getDestination() {
    return destination;
  }


  /**
   * Retrieves the departure time as a LocalTime object.
   *
   * @return The LocalTime object representing the departure time.
   */
  public LocalTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Retrieves the delay represented as a LocalTime.
   *
   * @return The LocalTime object representing the delay.
   */
  public LocalTime getDelay() {
    return delay;
  }
}
