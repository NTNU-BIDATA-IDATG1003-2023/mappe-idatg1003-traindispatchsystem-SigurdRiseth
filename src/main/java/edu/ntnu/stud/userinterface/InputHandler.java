package edu.ntnu.stud.userinterface;

import java.time.LocalTime;
import java.util.Scanner;

public class InputHandler {
  private final StringManager stringManager;
  private final Scanner scanner = new Scanner(System.in);

  public InputHandler(StringManager stringManager) {
    this.stringManager = stringManager;
  }

  public String getStringInput() {
    return scanner.nextLine();
  }

  public int getTrainNumberUnused() {
    int trainNumber = -1;

    while (trainNumber < 1) {
      stringManager.printTrainNumberAsk(); //TODO: PRINT I STRINGMANAGER
      trainNumber = scanner.nextInt();
      scanner.nextLine();

      if (trainNumber < 1) {
        stringManager.printTrainNumberInvalid();
      } else if (stringManager.getStation().trainExists(trainNumber)) {
        stringManager.printTrainNumberInUse();
        trainNumber = -1;
      }
    }
    return trainNumber;
  }


  public String getLine() {
    stringManager.printLineAsk();
    return scanner.nextLine();
  }

  public String getDestination() {
    stringManager.printDestinationAsk();
    String destination = scanner.nextLine();
    return destination.substring(0, 1).toUpperCase()
        + destination.substring(1).toLowerCase();
  }

  public LocalTime getLocalTimeFromString() {
    LocalTime departureTime = null;
    while (departureTime == null) {
      stringManager.printTimeAsk();
      String inputDepartureTime = scanner.nextLine();
      if (departureTimeValid(inputDepartureTime)) {
        departureTime = LocalTime.parse(inputDepartureTime);
        return departureTime;
      } else {
        stringManager.printTimeInvalid();
      }
    }
    return departureTime;
  }

  private boolean departureTimeValid(String inputDepartureTime) {
    try {
      LocalTime.parse(inputDepartureTime);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getTrack() {
    stringManager.printTrackAsk();
    return scanner.nextLine();
  }

  public int getTrainNumberInUse() {
    int trainNumber = -1;

    while (trainNumber < 1) {
      stringManager.printTrainNumberAsk();
      trainNumber = scanner.nextInt();
      scanner.nextLine();

      if (trainNumber < 1) {
        stringManager.printTrainNumberInvalid();
      } else if (!stringManager.getStation().trainExists(trainNumber)) {
        stringManager.printTrainNumberNotInUse();
        trainNumber = -1;
      }
    }
    return trainNumber;
  }

}


