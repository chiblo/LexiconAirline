package flight_control;

import domain.FlightReservation;
import domain.Passenger;
import domain.Plane;
import enumeration.City;
import enumeration.KeyFlightTicketClass;
import enumeration.TicketClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by Bardia on 2016-07-12.
 */
public class WaitingLists {

    private static TreeMap<KeyFlightTicketClass, ArrayList<FlightReservation>> waitingLists = new TreeMap<>();
    private static HashMap<City, HashMap<City, Integer>> sizeOfWaitingLists = new HashMap<>();

    static {
        City[] cities = {City.BERLIN, City.PARIS, City.ROME, City.STOCKHOLM};
        for (int i = 0; i < 4; i++) {
            sizeOfWaitingLists.put(cities[i], new HashMap<>());
            for (int j = 0; j < 4; j++)
                if (cities[i] != cities[j]) {
                    waitingLists.put(new KeyFlightTicketClass(cities[i], cities[j], TicketClass.ECONOMY_CLASS), new ArrayList<>());
                    waitingLists.put(new KeyFlightTicketClass(cities[i], cities[j], TicketClass.FIRST_CLASS), new ArrayList<>());
                    sizeOfWaitingLists.get(cities[i]).put(cities[j], 0);
                }

        }
    }


    public static void flightCoordinator(Plane plane) {
        City departure = plane.getStartingPoint();
        City destination = takeDestination(departure, plane.getFlightNumber());
        ArrayList<Passenger> passengers = addPassengersToFlight(
                new KeyFlightTicketClass(departure, destination, TicketClass.FIRST_CLASS), plane.getTotalCapacity() / 3);
        passengers.addAll(addPassengersToFlight(
                new KeyFlightTicketClass(departure, destination, TicketClass.ECONOMY_CLASS), 2 * plane.getTotalCapacity() / 3));
        plane.setPassengers(passengers);
    }

    private static ArrayList<Passenger> addPassengersToFlight(KeyFlightTicketClass keyFlightTicketClass, int capacity) {
        ArrayList<Passenger> flightList = new ArrayList<>();
        synchronized (waitingLists.get(keyFlightTicketClass)) {
            Iterator<FlightReservation> i = waitingLists.get(keyFlightTicketClass).iterator();
            while (i.hasNext() || flightList.size() != capacity) {
                FlightReservation fr = i.next();
                if ((fr.getNumberOfPassengers() + flightList.size()) <= capacity) {
                    flightList.addAll(fr.getPassengers());
                    i.remove();
                }
            }
        }
        return flightList;
    }

    private static City takeDestination(City departure, String flightNumber) {
        City destination = City.BERLIN;
        int max = -1;
        for (City city : sizeOfWaitingLists.get(departure).keySet()) {
            if (sizeOfWaitingLists.get(departure).get(city) > max) {
                max = sizeOfWaitingLists.get(departure).get(city);
                destination = city;
            }
        }

        System.out.println("Destination of flight " + flightNumber + " from " + departure + " set to " + destination +
                " with " + max + " passengers in waiting list.");
        return destination;
    }

    static synchronized void addToList(FlightReservation flightReservation) {

        waitingLists.get(flightReservation.getKeyFlightTicketClass()).add(flightReservation);
        sizeOfWaitingLists.get(flightReservation.getKeyFlightTicketClass().getDeparture())
                .put(flightReservation.getKeyFlightTicketClass().getDestination(),
                        sizeOfWaitingLists.get(flightReservation.getKeyFlightTicketClass().getDeparture())
                                .get(flightReservation.getKeyFlightTicketClass().getDestination()) +
                                flightReservation.getNumberOfPassengers());
    }


}

