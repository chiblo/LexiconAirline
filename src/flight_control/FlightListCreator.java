package flight_control;

import domain.FlightReservation;
import domain.Passenger;
import domain.Plane;
import enumeration.City;
import enumeration.TicketClass;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by User on 2016-07-12.
 */
public class FlightListCreator implements Callable<ArrayList<Passenger>> {
    private City departure;
    private City destination;
    private TicketClass ticketClass;
    private int maxCapacity;
    private Plane plane;
    private ArrayList<Passenger> flightList = new ArrayList<>();


    public FlightListCreator(Plane plane, TicketClass ticketClass) {
        this.plane = plane;
        this.departure = plane.getStartingPoint();
        this.destination = plane.getDestination();
        this.ticketClass = ticketClass;
        if (ticketClass == TicketClass.FIRST_CLASS) maxCapacity = 10;
        else maxCapacity = 20;
    }

    @Override
    public ArrayList<Passenger> call() throws Exception {
        boolean notCompleteList = true;
        while (notCompleteList) {
            synchronized (this) {

                notCompleteList = this.addFirst();
                this.addSecond();
                wait(100);
                notifyAll();
            }
        }
        return flightList;
    }

    private boolean addFirst() {
        synchronized (this) {
            for (FlightReservation i : WaitingLists.getWaitingList(departure, destination)) {
                if ((!i.isInFlight()) && (i.getTicketClass() == ticketClass) && (i.getNumberOfPassengers() + flightList.size() == maxCapacity)) {
                    this.addToTheList(i);
                    return false;
                }
            }
            return true;
        }
    }


    private void addSecond() {
        synchronized (this) {
            for (FlightReservation i : WaitingLists.getWaitingList(departure, destination)) {
                if ((!i.isInFlight()) && (i.getTicketClass() == ticketClass) && ((i.getNumberOfPassengers() + flightList.size()) < maxCapacity)) {
                    this.addToTheList(i);
                    break;
                }

            }
        }
    }

    private void addToTheList(FlightReservation reservation) {
        flightList.addAll(reservation.getPassengers());
        reservation.setPlane(plane);
        reservation.setFlightNumber(plane.getFlightNumber());
        reservation.setInFlight(true);
    }
}