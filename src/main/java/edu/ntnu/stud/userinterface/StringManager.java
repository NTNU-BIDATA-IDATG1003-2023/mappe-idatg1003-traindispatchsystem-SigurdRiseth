package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.util.Iterator;

/**
 * Class for managing all the methods creating strings used in the UI.
 */
public class StringManager {

  private final Station station;

  /**
   * Constructor that sets the station.
   *
   * @param station The station to be set
   */
  public StringManager(Station station) {
    this.station = station;
  }

  /**
   * Returns a String of the menu options.
   *
   * @return String
   */
  public void printOptions() {
    System.out.println(String.join("\n",
        "-------------------------------------------",
        "1: Print all upcoming departures",
        "2: Print all upcoming departures to a given destination",
        "3: Print the next departure to a given destination",
        "4: Add a new train departure",
        "5: Set delay for a train departure",
        "6: Set track for a train departure",
        "7: Remove track for a train departure",
        "8: Get train by train number",
        "9: Remove train by train number",
        "10: Set the clock",
        "0: Exit",
        "Please enter a number between 0 and 10:")
    );
  }

  public void print(String string) {
    System.out.println(string);
  }


  /**
   * Prints a String of all departures yet to depart.
   *
   */
  public void printAllDepartures() {
    StringBuilder result = new StringBuilder();
    result.append("The time is now " + station.getClock() + "\n");
    result.append("Here is a list of all the trains that are yet to depart:\n");
    result.append("Train number\tLine\tDestination\t\t\tDeparture time\tTrack\n");

    Iterator<TrainDeparture> iterator = station.getTrainDeparturesSorted().iterator();
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      String formattedLine = String.format("%-15s%-5s%-20s%-15s%-10s",
          trainDeparture.getTrainNumber(),
          trainDeparture.getLine(),
          trainDeparture.getDestination(),
          trainDeparture.getDepartureTime(),
          (trainDeparture.getTrack() == -1) ? "-" : String.valueOf(trainDeparture.getTrack())
      );
      result.append(formattedLine).append("\n");
    }
    System.out.println(result);
  }

  /**
   * Return all departures to a given destination. The destination can be given in any
   * capitalisation. If no departures are found, a message is returned.
   *
   * @param destination
   * @return String
   */
  public void printAllDeparturesToDestination(String destination) {
    destination = destination.substring(0, 1).toUpperCase()
        + destination.substring(1).toLowerCase();
    Iterator<TrainDeparture> iterator = station.getTrainDeparturesSorted().iterator();
    boolean foundDeparture = false;
    System.out.println("The time is now " + station.getClock());
    System.out.println("Here is a list of all the trains that are yet to depart to " + destination
        + ":");
    System.out.println("Train number\tLine\tDestination\t\t\tDeparture time\tTrack");
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      if (trainDeparture.getDestination().equals(destination)) {
        String formattedLine = String.format("%-15s | %-5s | %-20s | %-15s | %-10s",
            trainDeparture.getTrainNumber(),
            trainDeparture.getLine(),
            trainDeparture.getDestination(),
            trainDeparture.getDepartureTime(),
            (trainDeparture.getTrack() == -1) ? "-" : String.valueOf(trainDeparture.getTrack())
        );
        System.out.println(formattedLine);
        foundDeparture = true;
      }
    }
    if (!foundDeparture) {
      System.out.println("No trains to " + destination + " found.");
    }
  }

  /**
   * Prints the next departure to a given destination.
   * If no departures are found, a message is printed.
   *
   * @param destination2
   * @return String
   */
  public void printNextDepartureToDestination(String destination2) { // TODO: Dumt å kalle metoden flere ganger istedet for å lagre trainnumber og heller kalle get funksjonen til det spesifikke toget?
    if (station.getTrainDepartureByDestination(destination2) != null) {
      StringBuilder result = new StringBuilder();
      result.append("The next train to " + station.getTrainDepartureByDestination(destination2).getDestination());
      result.append(" departs at " + station.getTrainDepartureByDestination(destination2).getDepartureTime());
      if (station.getTrainDepartureByDestination(destination2).getTrack() == -1) {
        result.append(" from track (not yet assigned)");
      } else {
        result.append(" from track " + station.getTrainDepartureByDestination(destination2)
            .getTrack());
      }
      System.out.println(String.valueOf(result));
    } else {
      System.out.println("No train to " + destination2 + " was found.");
    }
  }

  public void printDestinationAsk() {
    System.out.println("Please enter a destination: ");
  }

  public void printTrainNumberAsk() {
    System.out.println("Please enter a train number: ");
  }

  public void printLineAsk() {
    System.out.println("Please enter a line: ");
  }

  public Station getStation() {
    return station;
  }

  public void printTrainNumberInvalid() {
    System.out.println(
        "The train number must be a whole number above 0. Please try again.");
  }

  public void printTrainNumberInUse() {
    System.out.println("The train number is already in use. Please try again.");
  }

  public void printTimeAsk() {
    System.out.println("Please enter time in format (hh:mm): ");
  }

  public void printTrackAsk() {
    System.out.println("Please enter a track (type \"none\" if you do not wish to assign one):");
  }

  public void printTimeInvalid() {
    System.out.println("Please try again and enter a valid time (in format hh:mm).");
  }

  public void printTrainNumberNotInUse() {
    System.out.println("The train number does not exist. Please try again.");
  }

  public void printTrainDeparture(TrainDeparture train) {
    System.out.println("Train number: " + train.getTrainNumber());
    System.out.println("Line: " + train.getLine());
    System.out.println("Destination: " + train.getDestination());
    System.out.println("Departure time: " + train.getDepartureTime());
    System.out.print("Track: ");
    System.out.println((train.getTrack() == -1) ? "Not yet assigned" : String.valueOf(train.getTrack()));
  }
}
