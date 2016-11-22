package flight_control;

import domain.FlightReservation;
import enumeration.City;

import java.util.ArrayList;


/**
 * Created by Bardia on 2016-07-12.
 */
public class WaitingLists {


    static ArrayList<FlightReservation> waitingListRomeParis = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListParisRome = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListRomeBerlin = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListBerlinRome = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListRomeStockholm = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListStockholmRome = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListBerlinParis = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListParisBerlin = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListBerlinStockholm = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListStockholmBerlin = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListStockholmParis = new ArrayList<>();
    static ArrayList<FlightReservation> waitingListParisStockholm = new ArrayList<>();


    public static City takeDestination(City departure, String flightNumber) {
        City destination = City.STOCKHOLM;
        int a = waitingListSize(departure, City.STOCKHOLM);
        int b = waitingListSize(departure, City.ROME);
        int c = waitingListSize(departure, City.BERLIN);
        int d = waitingListSize(departure, City.PARIS);


        if (a > Math.max(Math.max(b, c), d)) {
            destination = City.STOCKHOLM;
        }
        if (b > Math.max(Math.max(a, c), d)) {
            destination = City.ROME;
        }
        if (c > Math.max(Math.max(b, a), d)) {
            destination = City.BERLIN;
        }
        if (d > Math.max(Math.max(b, c), a)) {
            destination = City.PARIS;
        }

        System.out.println("From " + departure + " to " + City.STOCKHOLM + " " + a +
                " to " + City.ROME + " " + b +
                " to " + City.BERLIN + " " + c +
                " to " + City.PARIS + " " + d + ". Flight " + flightNumber + " destination set to " + destination);
        return destination;

    }

    static int waitingListSize(City departure, City destination) {
        int result = 0;
        if (departure != destination) {
        try {
            for (FlightReservation fr : getWaitingList(departure, destination)) {
                if (!fr.isInFlight())
                    result += fr.getNumberOfPassengers();
            }
        } catch (NullPointerException e) {
        }
        }
        return result;
    }

    public static void addToList(FlightReservation flightReservation) {
    //    try {
            getWaitingList(flightReservation.getDeparture(), flightReservation.getDestination()).add(flightReservation);
  //      } catch (ArrayIndexOutOfBoundsException e) {
  //      }
    }

    public static void printList(City departure, City destination) {
        getWaitingList(departure, destination).forEach(i -> System.out.println(i.getNumberOfPassengers()));
        System.out.println(waitingListSize(departure, destination));
    }

    public static ArrayList<FlightReservation> getWaitingList(City departure, City destination) {
        switch (departure) {
            case STOCKHOLM:
                switch (destination) {
                    case ROME: {
                        return waitingListStockholmRome;
                    }
                    case BERLIN: {
                        return waitingListStockholmBerlin;
                    }
                    case PARIS: {
                        return waitingListStockholmParis;
                    }
                }
            case ROME:
                switch (destination) {
                    case STOCKHOLM: {
                        return waitingListRomeStockholm;
                    }
                    case BERLIN: {
                        return waitingListRomeBerlin;
                    }
                    case PARIS: {
                        return waitingListRomeParis;
                    }
                }
            case BERLIN:
                switch (destination) {
                    case ROME: {
                        return waitingListBerlinRome;
                    }
                    case STOCKHOLM: {
                        return waitingListBerlinStockholm;
                    }
                    case PARIS: {
                        return waitingListBerlinParis;
                    }
                }
            case PARIS:
                switch (destination) {
                    case ROME: {
                        return waitingListParisRome;
                    }
                    case BERLIN: {
                        return waitingListParisBerlin;
                    }
                    case STOCKHOLM: {
                        return waitingListParisStockholm;
                    }
                }
        }

        return null;
    }


}

