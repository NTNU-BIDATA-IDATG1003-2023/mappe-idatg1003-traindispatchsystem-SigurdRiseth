package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.util.Iterator;

public class StringManager {

  private Station station;

  public StringManager(Station station) {
    this.station = station;
  }

  public String options() {
    String[] menuStrings = {
        "-------------------------------------------",
        "1: Print all upcoming departures",
        "2: Print all upcoming departures to a given destination",
        "3: Print the next departure to a given destination",
        "4: Add a new train departure",
        "5: Set delay for a train departure",
        "6: Set track for a train departure",
        "7: Get train by train number",
        "8: Remove train by train number",
        "9: Set the clock",
        "10: Exit",
        "Please enter a number between 1 and 10:"
    };

    return String.join("\n", menuStrings);
  }

  public String getAllDepartures() {
    StringBuilder result = new StringBuilder();
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

    return String.valueOf(result);
  }

  public String printAllDeparturesToDestination(String destination) {
    destination = destination.substring(0, 1).toUpperCase() + destination.substring(1).toLowerCase(); // Make it so it doesn't matter if the user types "oslo" or "Oslo"
    Iterator<TrainDeparture> iterator = station.getTrainDeparturesSorted().iterator();
    StringBuilder result = new StringBuilder();
    boolean foundDeparture = false;
    result.append("Here is a list of all the trains that are yet to depart to " + destination
        + ":\n");
    result.append("Train number\tLine\tDestination\t\t\tDeparture time\tTrack\n");
    while (iterator.hasNext()) {
      TrainDeparture trainDeparture = iterator.next();
      if (trainDeparture.getDestination().equals(destination)) {
        String formattedLine = String.format("%-15s%-5s%-20s%-15s%-10s",
            trainDeparture.getTrainNumber(),
            trainDeparture.getLine(),
            trainDeparture.getDestination(),
            trainDeparture.getDepartureTime(),
            (trainDeparture.getTrack() == -1) ? "-" : String.valueOf(trainDeparture.getTrack())
        );
        result.append(formattedLine).append("\n");
        foundDeparture = true;
      }
    }
    if (!foundDeparture) {
      return "No trains to " + destination + " found.";
    }
    return String.valueOf(result);
  }

}
