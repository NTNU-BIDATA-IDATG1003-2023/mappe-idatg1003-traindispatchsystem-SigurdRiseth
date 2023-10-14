package edu.ntnu.stud.traindeparture;

/**
 * Class for the train departures.
 *
 * Will store information such as train number, line, departure time, delay, destination and what track it will depart from.
 *
 * @author sigurdriseth
 * @version 0.0.1
 * @since 14.10.2023
 */
public class TrainDeparture {

    private int track;
    private int trainNumber;
    private String line;
    private Clock departureTime; // use a Clock class to store departureTime and delay.
    private Clock delay;
    private String destination;

}
