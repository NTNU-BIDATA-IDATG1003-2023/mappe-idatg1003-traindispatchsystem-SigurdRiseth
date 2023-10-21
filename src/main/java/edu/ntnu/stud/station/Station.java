package edu.ntnu.stud.station;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.util.Iterator;
import java.util.List;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Class for the train station.
 * <p>In charge of keeping track of the time and a list of all TrainDepartures yet to depart.</p>
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
   *
   */
  public void setClock(LocalTime time) {
    this.time = time;
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
   * Adds a TrainDeparture to the trainDepartures HashMap.
   *
   * @param trainDeparture An instance of the class TrainDeparture
   */
  public void addTrainDeparture(TrainDeparture trainDeparture) {
    this.trainDepartures.put(trainDeparture.getTrainNumber(), trainDeparture);
  }

  /**
   * Returns a list of all TrainDepartures yet to depart.
   *
   * @return trainDeparturesSorted
   */
  public List getTrainDeparturesSorted() {
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
   * Sorts the trainDepartures HashMap by departure time and stores the sorted list in trainDeparturesSorted.
   */
  public void sortByDepartureTime() {
    this.trainDeparturesSorted = this.trainDepartures.values().stream()
        .sorted(Comparator.comparing(TrainDeparture::getDepartureTime))
        .collect(Collectors.toList());
  }

  /**
   * Returns a boolean value indicating whether a train with the given train number exists.
   *
   * @param trainNumber
   * @return
   */
  public boolean trainExists(int trainNumber) {
    return trainDepartures.containsKey(trainNumber);
  }

  /**
   * Changes the track of a train with the given train number. Returns a string indicating whether the track was changed or not.
   * @param trainNumber
   * @param track
   * @return
   */
  public String changeTrackByTrainNumber(int trainNumber, int track) {
    if (trainExists(trainNumber)) {
      trainDepartures.get(trainNumber).setTrack(track);
      return "Track changed.";
    } else {
      return "Train does not exist.";
    }
  }

  /**
   * Changes the delay of a train with the given train number. Returns a string indicating whether the delay was changed or not.
   * @param trainNumber
   * @param delay
   * @return
   */
  public String changeDelayByTrainNumber(int trainNumber, LocalTime delay) {
    if (trainExists(trainNumber)) {
      trainDepartures.get(trainNumber).setDelay(delay);
      //TODO: bruk parse() for å få LocalTime fra String på formatet "HH:MM"
      return "Delay changed.";
    } else {
      return "Train does not exist.";
    }
  }

  /**
   * Returns a TrainDeparture with the given train number.
   * @param trainNumber
   * @return
   */
  public TrainDeparture getTrainDepartureByTrainNumber(int trainNumber) {
    if (trainExists(trainNumber)){
      return trainDepartures.get(trainNumber);
    } else {
      return null;
    }
  }

  /**
   * Returns the first train departure with the provided destination.
   * @param destination
   * @return
   */
  public TrainDeparture getTrainDepartureByDestination(String destination) {
    Iterator<TrainDeparture> iterator = this.trainDeparturesSorted.iterator();
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      if (trainDeparture.getDestination().equals(destination)) {
        return trainDeparture;
      }
    }
    return null;
  }

}
