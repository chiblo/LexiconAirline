package flight_control;

import domain.FlightReservation;
import domain.Passenger;
import enumeration.City;
import enumeration.TicketClass;

import java.util.ArrayList;

/**
 * Created by Bardia on 2016-07-11.
 */
public class OrderGenerator implements Runnable {

    @Override
    public void run() {
        for (int j = 0; j < 100; j++) {
            City departure = getRandomCity();
            FlightReservation order = new FlightReservation(((int) (Math.random() * 4.99) + 1),
                    getRandomTicketClass(), departure , setRandomDestination(departure));

            synchronized (this) {
                WaitingLists.addToList(order);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private City getRandomCity(){
        int randomize = (int) ((Math.random()) * 4);
        switch (randomize) {
            case 0: return City.STOCKHOLM;
            case 1: return City.PARIS;
            case 2: return City.BERLIN;
            case 3: return City.ROME;
        }
        return City.STOCKHOLM;
    }

    private City setRandomDestination(City departure){
        City destination;
        do {

            destination = getRandomCity();

        }while(destination == departure);
        return destination;
    }

    private TicketClass getRandomTicketClass(){
        if ( (int) (Math.random() * 1.5) == 1) {
            return TicketClass.FIRST_CLASS;
        }
        return TicketClass.ECONOMY_CLASS;
    }
}
