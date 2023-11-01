package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.station.Station;
import edu.ntnu.stud.traindeparture.TrainDeparture;
import java.util.Iterator;

public class StringManager {

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

  public String getAllDepartures(Station station) {
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

    return result.toString();
  }

}
