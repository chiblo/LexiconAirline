package domain;

import enumeration.*;

public class Passenger {
    private static int idCounter = 19830916;
    private String id;
    private Meal meal;
    private String seatNumber;
    private double ticketPrice;
    private FlightReservation flightReservation;

    public Passenger(FlightReservation flightReservation) {
        id = "PAS" + idCounter;
        idCounter += (Math.random()*10000000)-5000000;
        this.flightReservation = flightReservation;
        this.setRandomMeal();
        setTicketPrice();
    }

    private void setRandomMeal(){
        switch ((int) (Math.random() * 3.99)) {
            case 0:
                meal = Meal.MEAL_PACKAGE_1;
                break;
            case 1:
                meal = Meal.MEAL_PACKAGE_2;
                break;
            case 2:
                meal = Meal.MEAL_PACKAGE_3;
                break;
            case 3:
                meal = Meal.NO_MEAL;
        }
    }

    public void setTicketPrice() {
        this.ticketPrice = flightReservation.getBaseTicketPrice() + FoodPrice.getFoodPrice(this);
        flightReservation.setPrice();
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public Meal getMeal() {
        return meal;
    }

    public TicketClass getTicketClass() {
        return flightReservation.getTicketClass();
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getId() {
        return id;
    }

    public FlightReservation getFlightReservation() {
        return flightReservation;
    }

    public String getReservationNumber() {
        return flightReservation.getReservationNumber();
    }

    public City getDestination() {
        return flightReservation.getDestination();
    }

    public City getDeparture() {
        return flightReservation.getDeparture();
    }

    public String getSeatNumber() {
        return seatNumber;
    }

}
