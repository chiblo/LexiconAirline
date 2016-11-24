package domain;

import enumeration.City;
import enumeration.KeyFlightTicketClass;
import enumeration.TicketClass;

import java.util.ArrayList;

public class FlightReservation {

    private static int resNumber = 1000;

    private ArrayList<Passenger> passengers = new ArrayList<>();
    private Plane plane;
    private String reservationNumber;
    private String flightNumber;
    private double price;
    private int numberOfPassengers;
    private boolean isInFlight = false;
    private KeyFlightTicketClass keyFlightTicketClass;


    public FlightReservation(int numberOfPassengers, KeyFlightTicketClass keyFlightTicketClass) {
        this.keyFlightTicketClass = keyFlightTicketClass;
        this.numberOfPassengers = numberOfPassengers;
        this.reservationNumber = "RES" + resNumber;
        resNumber++;
        for (int i = 0; i < numberOfPassengers ; i++) {
            Passenger p = new Passenger(this);
            passengers.add(p);
        }
        setPrice();

    }


    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice() {
        for(Passenger p : passengers)
        this.price += p.getTicketPrice();
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public TicketClass getTicketClass() {
        return keyFlightTicketClass.getTicketClass();
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public boolean isInFlight() {
        return isInFlight;
    }

    public void setInFlight(boolean inFlight) {
        isInFlight = inFlight;
    }

    public City getDestination() {
        return keyFlightTicketClass.getDestination();
    }

    public City getDeparture() {
        return keyFlightTicketClass.getDeparture();
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public double getBaseTicketPrice() {
        return keyFlightTicketClass.getBaseTicketPrice();
    }
}
