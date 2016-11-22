package domain;

import enumeration.City;
import enumeration.Distance;
import enumeration.TicketClass;

import java.util.ArrayList;

public class FlightReservation {

    private static int resNumber = 1000;

    private ArrayList<Passenger> passengers = new ArrayList<>();
    private Plane plane;
    private String reservationNumber;
    private String flightNumber;
    private double price;
    private TicketClass ticketClass;
    private City destination;
    private City departure;
    private int numberOfPassengers;
    private boolean isInFlight = false;
    private double baseTicketPrice;


    public FlightReservation(int numberOfPassengers, TicketClass ticketClass, City destination, City departure) {
        this.ticketClass = ticketClass;
        this.departure = departure;
        this.destination = destination;
        this.numberOfPassengers = numberOfPassengers;
        this.reservationNumber = "RES" + resNumber;
        resNumber++;
        this.baseTicketPrice = ((Distance.takeDistance(departure, destination)) / 10) + 3500;
        if (this.ticketClass == TicketClass.FIRST_CLASS) baseTicketPrice *= 4;
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
        return ticketClass;
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
        return destination;
    }

    public City getDeparture() {
        return departure;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public double getBaseTicketPrice() {
        return baseTicketPrice;
    }
}
