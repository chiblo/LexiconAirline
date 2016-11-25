package domain;

import enumeration.City;
import enumeration.Distance;
import enumeration.PlaneStatus;
import enumeration.TicketClass;
import exceptions.PassengerNotFoundException;
import flight_control.WaitingLists;

import java.util.ArrayList;

public class Plane implements Runnable {

    private City destination;
    private City startingPoint;
    private String flightNumber;
    private PlaneStatus status;
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private int mileage;
    private int numberOfRows;
    private int ticketPrice;

    public Plane(String flightNumber, City startingPoint, int size) {
        this.startingPoint = startingPoint;
        this.flightNumber = flightNumber;
        this.numberOfRows = size * 3;
        this.status = PlaneStatus.ON_GROUND;
        this.mileage = (int) (Math.random() * 50000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.getPlaneInfo();
                Thread.sleep(1500);

                WaitingLists.flightCoordinator(this);
                Thread.sleep(1500);

                this.setTicketPrice();
                status = PlaneStatus.BOARDING;
                this.getPlaneInfo();
                System.out.println("Total ticket price is " + this.ticketPrice + ". Our profit is " + (this.ticketPrice) * .3 + ".");
                Thread.sleep(1500);

                status = PlaneStatus.IN_FLIGHT;
                this.getPlaneInfo();

                int distance = Distance.takeDistance(startingPoint, destination);
                mileage += distance;
                Thread.sleep(distance / 2);
                if (mileage >= 50000) {
                    this.status = PlaneStatus.NEED_REPAIR;
                    getPlaneInfo();
                }

                Thread.sleep(distance / 2);
                if (status == PlaneStatus.NEED_REPAIR) {
                    this.status = PlaneStatus.MAINTENANCE;
                    getPlaneInfo();
                    Thread.sleep(((int) (Math.random()) * 10000) + 2000);
                    mileage = 0;
                }

                Passenger.addToFormerCustomers(passengers);

                startingPoint = destination;
                status = PlaneStatus.ON_GROUND;

            } catch (InterruptedException e) {
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


    private void getPlaneInfo() {
        if(status == PlaneStatus.IN_FLIGHT)
            System.out.println("\u001B[32m========================================================");

        printStatus();

        if(status == PlaneStatus.IN_FLIGHT)
            System.out.println("\u001B[32m========================================================");

        if(status == PlaneStatus.NEED_REPAIR)
            System.out.println("\u001B[31m^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }

    private void printStatus(){
        StringBuffer sb = new StringBuffer("Flight ");
        sb.append(flightNumber);

        switch (status) {
            case ON_GROUND:
                sb.append(" is ready for boarding process at ");
                sb.append(startingPoint);
                break;

            case BOARDING:
                sb.append(" is boarding passengers at ");
                sb.append(startingPoint);
                sb.append(". And will depart shortly toward ");
                sb.append(destination);
                break;

            case IN_FLIGHT:
                sb.append(" has departed from ");
                sb.append(startingPoint);
                sb.append(" toward ");
                sb.append(destination);
                break;

            case MAINTENANCE:
                sb.append(" is under maintenance at ");
                sb.append(destination);
                sb.append(". And will join the squadron shortly");
                break;

            case NEED_REPAIR:
                sb.append(" further flights has been canceled due to technical issue. It will be repaired at ");
                sb.append(destination);
        }
        sb.append(".");
        System.out.println(sb);

    }

    private void giveSitNumber(ArrayList<Passenger> passengers) {
        int firstClassCounter = 0;
        int secondClassCounter = numberOfRows * 2;
        int seat;

        for (Passenger p : passengers) {
            if (p.getTicketClass() == TicketClass.ECONOMY_CLASS) seat = secondClassCounter++;
            else seat = firstClassCounter++;
            switch (seat % 6) {
                case 0:
                    p.setSeatNumber(((seat / 6) + 1) + "A");
                    break;
                case 1:
                    p.setSeatNumber(((seat / 6) + 1) + "B");
                    break;
                case 2:
                    p.setSeatNumber(((seat / 6) + 1) + "C");
                    break;
                case 3:
                    p.setSeatNumber(((seat / 6) + 1) + "D");
                    break;
                case 4:
                    p.setSeatNumber(((seat / 6) + 1) + "E");
                    break;
                case 5:
                    p.setSeatNumber(((seat / 6) + 1) + "F");
                    break;
            }
        }

    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public String getFlightNumber() {
        return flightNumber;
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

    public void setTicketPrice() {
        for (Passenger p : this.getPassengers()) {
            this.ticketPrice += p.getTicketPrice();
        }
    }

    public int getTotalCapacity() {
        return numberOfRows * 6;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }
}
