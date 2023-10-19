package edu.ntnu.stud.station;

import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Class for the train station.
 * <p>In charge of keeping track of the time and a list of all TrainDepartures yet to depart.</p>
 */
public class Station {

  private LocalTime time;
  private HashMap<Integer, TrainDeparture> trainDepartures;

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

  public ArrayList getTrainDeparturesSorted() {
    return this.trainDeparturesSorted;
  }

  /**
   * Returns a list of all TrainDepartures yet to depart.
   */
  public void sortByDepartureTime() {
    this.trainDeparturesSorted = this.trainDepartures.entrySet().stream()
        .sorted(Comparator.comparing(e -> e.getValue().getDepartureTime()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2, HashMap::new));
  }

  public boolean trainExists(int trainNumber) {
    return trainDepartures.containsKey(trainNumber);
  }

}
