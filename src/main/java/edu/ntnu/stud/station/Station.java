package edu.ntnu.stud.station;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class for the train station.
 * <p>
 * In charge of keeping track of the station-clock and a HashMap of all TrainDepartures yet to depart.
 * Contains methods for editing the train departures, returning train departures and change the clock.
 * </p>
 *
 * @author Sigurd Riseth
 * @version 0.0.1
 * @see TrainDeparture
 * @since 14.10.2023
 */
public class Station {

  private final HashMap<Integer, TrainDeparture> trainDepartures;
  private LocalTime clock;

  /**
   * Constructor that sets the clock to midnight and creates a new HashMap for the train departures.
   */
  public Station() {
    this.clock = LocalTime.of(0, 0);
    this.trainDepartures = new HashMap<>();
  }

  /**
   * Returns the station clock.
   *
   * @return the current clock
   */
  public LocalTime getClock() {
    return this.clock;
  }

  /**
   * Method that sets the station clock.
   *
   * <p>
   * Will only set the clock if the given time is after the current clock. In other words the clock can
   * only be set forwards.
   * </p>
   *
   * @param time The time to be set
   */
  public void setClock(LocalTime time) {
    if (this.clock.isBefore(time)) {
      this.clock = time;
    }
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
    if (!trainExists(trainNumber)) {
      this.trainDepartures.put(trainNumber,
          new TrainDeparture(track, trainNumber, line, destination,
              departureTime));
    }
  }

  /**
   * Returns an iterator of all TrainDepartures yet to depart.
   *
   * <p>
   * Sorts the trainDepartures HashMap by departure time and filters out departures that have
   * already departed based on the current station-clock. Returns an iterator of the remaining departures.
   * </p>
   *
   * @return Iterator of all TrainDepartures yet to depart
   */
  public Iterator<TrainDeparture> getTrainDeparturesSorted() {
    return this.trainDepartures // TODO: endre hashmapet og heller returnere iterator etterpå? Slette departures som har gått?
        .values()
        .stream()
        .filter(trainDeparture -> !trainDeparture.getDepartureTimeWithDelay().isBefore(this.clock))
        .sorted(Comparator.comparing(TrainDeparture::getDepartureTime))
        .iterator();
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
   * Changes the track of a train with the given train number.
   *
   * @param trainNumber The train number of the train to be changed
   * @param track       The track to be set
   */
  public void changeTrackByTrainNumber(int trainNumber, String track) {
    if (trainExists(trainNumber)) {
      trainDepartures.get(trainNumber).setTrack(track);
    }
  }

  /**
   * Changes the delay of a train with the given train number.
   *
   * <p>
   * If departure time + delay is greater than 23:59 the train is removed. Else the delay is set.
   * </p>
   *
   * @param trainNumber The train number of the train to be changed
   * @param delay       The delay to be set
   */
  public void changeDelayByTrainNumber(int trainNumber, LocalTime delay) {
    if (trainExists(trainNumber)) {
      if ((trainDepartures.get(trainNumber).getDepartureTime().getHour() + delay.getHour()) * 60
          + trainDepartures.get(trainNumber).getDepartureTime().getMinute() + delay.getMinute()
          >= 1440) {
        removeTrainDepartureByTrainNumber(trainNumber);
      } else {
        trainDepartures.get(trainNumber).setDelay(delay);
      }
    }
  }

  /**
   * Returns a TrainDeparture with the given train number.
   *
   * @param trainNumber The train number of the train to be returned
   * @return TrainDeparture
   */
  public TrainDeparture getTrainDepartureByTrainNumber(int trainNumber) {
    TrainDeparture td = null;
    if (trainExists(trainNumber)) {
      td = trainDepartures.get(trainNumber);
    }
    return td;
  }

  /**
   * Returns the first train departure with the provided destination. If no train departures are
   * found, null is returned.
   *
   * @param destination The destination of the train to be returned
   * @return TrainDeparture with the given destination
   */
  public TrainDeparture getTrainDepartureByDestination(String destination) {
    TrainDeparture trainDeparture = null;
    boolean trainDepartureFound = false;
    Iterator<TrainDeparture> iterator = getTrainDeparturesSorted();

    while (iterator.hasNext() && !trainDepartureFound) {
      trainDeparture = iterator.next();
      if (trainDeparture.getDestination().equals(destination)) {
        trainDepartureFound = true;
      }
    }
    if (!trainDepartureFound) {
      trainDeparture = null;
    }
    return trainDeparture;
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
  public int getAmountOfTrainDeparturesToDepart() { //TODO: Gjøre denne til boolean og kalle den isEmpty? Bedre for prossessor?
    Iterator<TrainDeparture> iterator = getTrainDeparturesSorted();
    int amount = 0;
    while (iterator.hasNext()) {
      iterator.next();
      amount++;
    }
    return amount;
  }

}
