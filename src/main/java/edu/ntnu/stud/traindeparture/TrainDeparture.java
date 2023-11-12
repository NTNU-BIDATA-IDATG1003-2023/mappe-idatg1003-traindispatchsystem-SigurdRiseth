package edu.ntnu.stud.traindeparture;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.userinterface.UserInterface;
import java.time.LocalTime;
import java.util.Objects;

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
  private final Station station;

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
    this.station = UserInterface.getStation();
    this.setTrack(track);
    this.setTrainNumber(trainNumber);
    this.setLine(line);
    this.setDestination(destination);
    this.setDepartureTime(departureTime);
    this.delay = LocalTime.of(0, 0);
  }

  /**
   * Sets the track. If the track is under 1 the value is set to -1.
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
   * Sets the train number.
   * <p>Also checks if the trainNumber is above 0 and not already stored in Stations HashMap.
   * If a train already exists with that number or it is under 1 the value is set to -1.</p>
   */
  public void setTrainNumber(
      int trainNumber) { // TODO: Denne og andre metoder brukes bare av konstruktøren. Kan jeg da sette den til private?
    if ((trainNumber > 0) && !this.station.trainExists(trainNumber)) {
      this.trainNumber = trainNumber;
    } else {
      this.trainNumber = -1;
    }
  }

  /**
   * Sets the line. Can also be set to null.
   *
   * @param line line to be set
   */
  public void setLine(String line) {
    this.line = line;
  }

  /**
   * Sets the destination. If destination is null, it will be set to "Invalid destination".
   *
   * @param destination destination to be set
   */
  public void setDestination(String destination) {
    this.destination = Objects.requireNonNullElse(destination, "Invalid destination");
  }

  /**
   * Sets the departure time. Can only be set to a time after the current time.
   *
   * @param departureTime departure time to be set
   */
  public void setDepartureTime(LocalTime departureTime) {
    if (departureTime.isAfter(station.getClock())) {
      this.departureTime = departureTime;
    }
  }

  /**
   * Sets the delay. If the delay is null the delay is set to 00:00. Removed from station if delayed
   * over midnight
   *
   * @param delay delay to be set
   */
  public void setDelay(LocalTime delay) {
    // TODO: delay burde kanskje ikke være 00:00 om man får inn null?
    // TODO: Kan man endre delay slik at reel deparutre time blir før klokken?
    if (delay != null) {
      if ((this.departureTime.getHour() + delay.getHour()) * 60
          + this.departureTime.getMinute() + delay.getMinute() >= 1440) {
        station.removeTrainDepartureByTrainNumber(this.trainNumber);
      } else {
        this.delay = delay;
      }
    } else {
      this.delay = LocalTime.of(0, 0);
    }
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
   * Retrieves the departure time (delay included) as a LocalTime object.
   *
   * @return The LocalTime object representing the departure time.
   */
  public LocalTime getDepartureTime() {
    return this.departureTime.plusHours(this.delay.getHour())
        .plusMinutes(this.delay.getMinute());
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
