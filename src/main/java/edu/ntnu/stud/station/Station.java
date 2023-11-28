package edu.ntnu.stud.station;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for the train station.
 * <p>In charge of keeping track of the time and a list of all TrainDepartures yet to depart.</p>
 *
 * @version 0.0.1
 * @Author Sigurd Riseth
 * @since 14.10.2023
 */
public class Station {

  private final HashMap<Integer, TrainDeparture> trainDepartures;
  private LocalTime time;
  private List<TrainDeparture> trainDeparturesSorted;

  /**
   * Constructor that sets the time to midnight and creates a new HashMap for the train departures.
   */
  public Station() {
    this.time = LocalTime.of(0, 0);
    this.trainDepartures = new HashMap<>();
  }

  /**
   * Method that sets the station clock.
   * <p>Will only set the time if the given time is after the current time.
   * Returns a boolean depending on the outcome of the change.
   * True is returned it the clock was changed, otherwise it returns false. </p>
   *
   * @param time The time to be set
   * @return boolean indicating whether the clock was changed or not
   */
  public boolean setClock(LocalTime time) {
    if (this.time.isBefore(time)) {
      this.time = time;
      return true;
    } else {
      return false;
    }
  }


  /**
   * Returns the station clock.
   *
   * @return the current time
   */
  public LocalTime getClock() {
    return this.time;
  }

  /**
   * Creates a new TrainDeparture and adds it to the trainDepartures HashMap using the addTrainDeparture() method.
   *
   * @param track         The track the train will depart from
   * @param trainNumber   The unique train ID
   * @param line          The line the train is driving
   * @param destination   The ends destination of the train
   * @param departureTime The time the train is set to depart
   */
  public void createTrainDeparture(String track, int trainNumber, String line, String destination,
      LocalTime departureTime) {
    this.addTrainDeparture(new TrainDeparture(track, trainNumber, line, destination,
        departureTime));
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
   * Returns an iterator of all TrainDepartures yet to depart. Done by running the sortByDepartureTime() method.
   *
   * @return an iterator of all TrainDepartures yet to depart
   */
  public Iterator<TrainDeparture> getTrainDeparturesSorted() {
    sortByDepartureTime();
    return this.trainDeparturesSorted.iterator();
  }

  /**
   * Sorts the trainDepartures HashMap by departure time and filters out departures that have
   * already departed based on the current time. Saves the result in the trainDeparturesSorted
   * list.
   */
  private void sortByDepartureTime() { // TODO: Hvorfor må denne lagres i klassen og ikke bare returneres direkte uten å lagres? Kunne vært en metode og ikke to?
    this.trainDeparturesSorted = this.trainDepartures
        .values()
        .stream()
        .filter(trainDeparture -> !trainDeparture.getDepartureTimeWithDelay().isBefore(this.time))
        .sorted(Comparator.comparing(TrainDeparture::getDepartureTime))
        .toList();
  }


  /**
   * Returns a boolean value indicating whether a train with the given train number exists.
   *
   * @param trainNumber The train number to be checked
   * @return boolean indicating whether the train exists or not
   */
  public boolean trainExists(int trainNumber) {
    return trainDepartures.containsKey(trainNumber);
  }

  /**
   * Changes the track of a train with the given train number. Returns a boolean indicating whether
   * the track was changed or not.
   *
   * @param trainNumber The train number of the train to be changed
   * @param track       The track to be set
   * @return boolean indicating whether the track was changed or not
   */
  public boolean changeTrackByTrainNumber(int trainNumber, String track) {
    if (trainExists(trainNumber)) {
      trainDepartures.get(trainNumber).setTrack(track);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Changes the delay of a train with the given train number. Returns an int indicating the effect it had on the train departure.
   *
   * @param trainNumber The train number of the train to be changed
   * @param delay       The delay to be set
   * @return int indicating the outcome of the result.
   */
  public int changeDelayByTrainNumber(int trainNumber, LocalTime delay) {
    if (trainExists(trainNumber)) {
      if ((trainDepartures.get(trainNumber).getDepartureTime().getHour() + delay.getHour()) * 60
          + trainDepartures.get(trainNumber).getDepartureTime().getMinute() + delay.getMinute()
          >= 1440) {
        removeTrainDepartureByTrainNumber(trainNumber);
        return 1;
      } else {
        trainDepartures.get(trainNumber).setDelay(delay);
        return 2;
      }
    } else {
      return 3;
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
   * Returns the first train departure with the provided destination. If no train departures are found, null is returned.
   *
   * @param destination The destination of the train to be returned
   * @return TrainDeparture
   */
  public TrainDeparture getTrainDepartureByDestination(String destination) {
    Iterator<TrainDeparture> iterator = getTrainDeparturesSorted();
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

  /**
   * Returns a list of all destinations of the train departures yet to depart.
   *
   * @return amount of train departures yet to depart
   */
  public int getAmountOfTrainDepartures() {
    sortByDepartureTime();
    return trainDeparturesSorted.size();
  }

}
