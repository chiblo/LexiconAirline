package domain;

import enumeration.City;
import enumeration.Distance;
import enumeration.PlaneStatus;
import enumeration.TicketClass;
import exceptions.PassengerNotFoundException;
import flight_control.FlightListCreator;
import flight_control.WaitingLists;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Plane implements Runnable {

    private City destination;
    private City startingPoint;
    private String flightNumber;
    private PlaneStatus status;
    private ArrayList<Passenger> firstClass = new ArrayList<>();
    private ArrayList<Passenger> economyClass = new ArrayList<>();
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private double ticketPrice;
    private int mileage;
    private ExecutorService pool = Executors.newFixedThreadPool(2);


    public Plane(String flightNumber, City startingPoint) {
        this.startingPoint = startingPoint;
        this.flightNumber = flightNumber;
        this.status = PlaneStatus.ON_GROUND;
        this.mileage = (int) (Math.random() * 50000);

    }

    @Override
    public void run() {
        while (true) {
            try {
                this.getPlaneInfo();
                destination = WaitingLists.takeDestination(startingPoint, flightNumber);
                Thread.sleep(1500);


                synchronized (this) {
                    try {
                        firstClass = pool.submit(new FlightListCreator(this, TicketClass.FIRST_CLASS)).get();
                        economyClass = pool.submit(new FlightListCreator(this, TicketClass.ECONOMY_CLASS)).get();
                        Thread.sleep(1500);

                    } catch (NullPointerException e) {
                    }

                }
                Thread.sleep(1500);

                giveSitNumber(firstClass, TicketClass.FIRST_CLASS);
                giveSitNumber(economyClass, TicketClass.ECONOMY_CLASS);
                this.setTicketPrice();


                status = PlaneStatus.BOARDING;
                this.getPlaneInfo();
                System.out.println("Total ticket price is " + this.ticketPrice + ". Our profit is " + (this.ticketPrice) * .3 + ".");


                Thread.sleep(1500);

                status = PlaneStatus.IN_FLIGHT;
                System.out.println("========================================================");
                this.getPlaneInfo();
                System.out.println("========================================================");

                int distance = Distance.takeDistance(startingPoint, destination);
                mileage += distance;
                Thread.sleep(distance / 2);
                if (mileage >= 50000) {
                    this.status = PlaneStatus.NEED_REPAIR;
                    getPlaneInfo();
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

                }

                Thread.sleep(distance / 2);
                if (status == PlaneStatus.NEED_REPAIR) {
                    this.status = PlaneStatus.MAINTENANCE;
                    getPlaneInfo();
                    Thread.sleep(((int) (Math.random()) * 10000) + 2000);
                    mileage = 0;
                }

                startingPoint = destination;
                status = PlaneStatus.ON_GROUND;

            } catch (InterruptedException e) {
                e.printStackTrace();

            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    public Passenger findPassengerById(String id) throws PassengerNotFoundException {
        for (Passenger passenger : passengers) {
            if (passenger.getId().equals(id)) {
                return passenger;
            } else {
                System.out.println("passenger with id: " + id + " not found");
            }
        }
        throw new PassengerNotFoundException();
    }


    public void setFirstClass(ArrayList<Passenger> firstClass) {
        this.firstClass.addAll(firstClass);
    }

    public void setEconomyClass(ArrayList<Passenger> economyClass) {
        this.economyClass.addAll(economyClass);
    }


    public void getPlaneInfo() {
        StringBuffer sb = new StringBuffer("Flight ");
        sb.append(flightNumber);

        if (status == PlaneStatus.ON_GROUND) {
            sb.append(" is ready for boarding process at ");
            sb.append(startingPoint);
        }
        if (status == PlaneStatus.BOARDING) {
            sb.append(" is boarding passengers at ");
            sb.append(startingPoint);
            sb.append(". And will depart shortly toward ");
            sb.append(destination);
        }
        if ((status == PlaneStatus.IN_FLIGHT)) {
            sb.append(" has departed from ");
            sb.append(startingPoint);
            sb.append(" toward ");
            sb.append(destination);
        }
        if ((status == PlaneStatus.MAINTENANCE)) {
            sb.append(" is under maintenance at ");
            sb.append(destination);
            sb.append(". And will join the squadron shortly");
        }

        if ((status == PlaneStatus.NEED_REPAIR)) {
            sb.append(" further flights has been canceled due to technical issue. It will be repaired at ");
            sb.append(destination);
        }
        sb.append(".");
        System.out.println(sb);
    }


    static void giveSitNumber(ArrayList<Passenger> passengers, TicketClass ticketClass) {
        int j = 1;

        if (ticketClass == TicketClass.ECONOMY_CLASS) j = 3;

        for (int i = 0; i < passengers.size(); i++) {
            switch (i % 5) {

                case 0:
                    passengers.get(i).setSeatNumber(((i / 5) + j) + "A");
                case 1:
                    passengers.get(i).setSeatNumber(((i / 5) + j) + "B");
                case 2:
                    passengers.get(i).setSeatNumber(((i / 5) + j) + "C");
                case 3:
                    passengers.get(i).setSeatNumber(((i / 5) + j) + "D");
                case 4:
                    passengers.get(i).setSeatNumber(((i / 5) + j) + "E");

            }
        }

    }


    public ArrayList<Passenger> getPassengers() {
        passengers = this.firstClass;
        passengers.addAll(economyClass);
        return passengers;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public PlaneStatus getStatus() {
        return status;
    }

    public void setStatus(PlaneStatus status) {
        this.status = status;
    }

    public City getDestination() {
        return destination;
    }

    public City getStartingPoint() {
        return startingPoint;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice() {
        for (Passenger p : this.getPassengers()) {
            this.ticketPrice += p.getTicketPrice();

        }
    }
}
