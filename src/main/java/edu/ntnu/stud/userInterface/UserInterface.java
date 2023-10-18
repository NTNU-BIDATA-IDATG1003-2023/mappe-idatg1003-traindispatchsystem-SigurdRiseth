package edu.ntnu.stud.userInterface;

import edu.ntnu.stud.TrainDispatchApp;
import edu.ntnu.stud.station.Station;

public class UserInterface {

  private Station station;

  public void start(){
    System.out.println("Welcome to the train dispatch app!");
  }

  public void init(){
    this.station = new Station();
  }

}
