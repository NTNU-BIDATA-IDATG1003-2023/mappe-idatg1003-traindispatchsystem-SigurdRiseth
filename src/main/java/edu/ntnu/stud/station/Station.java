package edu.ntnu.stud.station;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.util.*;
import java.time.LocalTime;
import java.util.stream.Collectors;

/**
 * Class for the train station.
 * <p>In charge of keeping track of the time and a list of all TrainDepartures yet to depart.</p>
 *
 * @Author Sigurd Riseth
 * @version 0.0.1
 * @since 14.10.2023
 */
public class Station {

  private LocalTime time;
  private HashMap<Integer, TrainDeparture> trainDepartures;
  private List<TrainDeparture> trainDeparturesSorted;

  /**
   * Constructor that sets the time to midnight and creates a new HashMap for the train departures.
   */
  public Station() {
    this.time = LocalTime.of(0, 0);
    this.trainDepartures = new HashMap<>();
  }

  /**
   * Sets the time.
   *
   * @param time The time to be set
   */
  public String setClock(LocalTime time) { //TODO : trengs try catch om den mottar LocalTime?
    try {
      if (this.time.isBefore(time)) {
        this.time = time;
        return "Time set to " + time.toString();
      } else {
        return "Time cannot be set to a time before the current time.";
      }
    } catch (Exception e) {
      return "Please enter a valid time on the format hh:mm";
    }
  }


  /**
   * Returns the time.
   *
   * @return time
   */
  public LocalTime getClock() {
    return this.time;
  }

  /**
   * Creates a new TrainDeparture and adds it to the trainDepartures HashMap.
   *
   * @param track         The track the train will depart from
   * @param trainNumber   The unique train ID
   * @param line          The line the train is driving
   * @param destination   The ends destination of the train
   * @param departureTime The time the train is set to depart
   */
  public void createTrainDeparture(String track, int trainNumber, String line, String destination,
      LocalTime departureTime) {
    TrainDeparture trainDeparture = new TrainDeparture(track, trainNumber, line, destination,
        departureTime, this);
    this.addTrainDeparture(trainDeparture);
  }

  /**
   * Adds a TrainDeparture to the trainDepartures HashMap.
   *
   * @param trainDeparture An instance of the class TrainDeparture
   */
  private void addTrainDeparture(TrainDeparture trainDeparture) {
    this.trainDepartures.put(trainDeparture.getTrainNumber(), trainDeparture);
  }

  /**
   * Returns a list of all TrainDepartures yet to depart, sorted by departure time.
   *
   * @return trainDeparturesSorted
   */
  public List<TrainDeparture> getTrainDeparturesSorted() {
    this.sortByDepartureTime();
    Iterator<TrainDeparture> iterator = this.trainDeparturesSorted.iterator();
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      if (trainDeparture.getDepartureTime().isBefore(this.time)) {
        iterator.remove();
      }
    }
    return this.trainDeparturesSorted;
  }

  /**
   * Sorts the trainDepartures HashMap by departure time and stores the sorted list in
   * trainDeparturesSorted.
   */
  public void sortByDepartureTime() { // TODO: filter out departures that have already departed here?
    this.trainDeparturesSorted = this.trainDepartures
        .values()
        .stream()
        .sorted(Comparator.comparing(TrainDeparture::getDepartureTime))
        .collect(Collectors.toList());
  }

  /**
   * Returns a boolean value indicating whether a train with the given train number exists.
   *
   * @param trainNumber The train number to be checked
   * @return boolean
   */
  public boolean trainExists(int trainNumber) {
    return trainDepartures.containsKey(trainNumber);
  }

  /**
   * Changes the track of a train with the given train number. Returns a string indicating whether
   * the track was changed or not.
   *
   * @param trainNumber The train number of the train to be changed
   * @param track       The track to be set
   * @return String
   */
  public String changeTrackByTrainNumber(int trainNumber, String track) {
    if (trainExists(trainNumber)) {
      trainDepartures.get(trainNumber).setTrack(track);
      return "Track changed.";
    } else {
      return "Train does not exist.";
    }
  }

  /**
   * Changes the delay of a train with the given train number. Returns a string indicating whether
   * the delay was changed or not.
   *
   * @param trainNumber The train number of the train to be changed
   * @param delay       The delay to be set
   * @return String
   */
  public String changeDelayByTrainNumber(int trainNumber, LocalTime delay) {
    if (trainExists(trainNumber)) {
      trainDepartures.get(trainNumber).setDelay(delay);
      return "Delay changed.";
    } else {
      return "Train does not exist. Please try again.";
    }
  }

  /**
   * Returns a TrainDeparture with the given train number.
   *
   * @param trainNumber The train number of the train to be returned
   * @return TrainDeparture
   */
  public TrainDeparture getTrainDepartureByTrainNumber(int trainNumber) {
    if (trainExists(trainNumber)) {
      return trainDepartures.get(trainNumber);
    } else {
      return null;
    }
  }

  /**
   * Returns the first train departure with the provided destination.
   *
   * @param destination The destination of the train to be returned
   * @return TrainDeparture
   */
  public TrainDeparture getTrainDepartureByDestination(String destination) {
    Iterator<TrainDeparture> iterator = getTrainDeparturesSorted().iterator();
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      if (trainDeparture.getDestination().equals(destination)) {
        return trainDeparture;
      }
    }
    return null;
  }

  /**
   * Removes a train departure with the given train number from the trainDepartures HashMap.
   *
   * @param trainNumber The train number of the train to be removed
   */
  public void removeTrainDepartureByTrainNumber(int trainNumber) {
    if (trainExists(trainNumber)) {
      trainDepartures.remove(trainNumber);
    }
  }

}
