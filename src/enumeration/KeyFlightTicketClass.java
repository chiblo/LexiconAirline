package enumeration;

/**
 * Created by Bardia on 2016-11-24.
 */
public class KeyFlightTicketClass implements Comparable<KeyFlightTicketClass>{

    private City departure;
    private City destination;
    private TicketClass ticketClass;
    private double baseTicketPrice;


    public KeyFlightTicketClass(City departure, City destination, TicketClass ticketClass) {
        this.departure = departure;
        this.destination = destination;
        this.ticketClass = ticketClass;
        this.baseTicketPrice = ((Distance.takeDistance(departure, destination)) / 10) + 3500;
        if (this.ticketClass == TicketClass.FIRST_CLASS) baseTicketPrice *= 4;
    }

    @Override
    public int compareTo(KeyFlightTicketClass that) {
        return departure.compareTo(that.departure)*10000 +
                destination.compareTo(that.destination)*100 +
                ticketClass.compareTo(that.ticketClass);
    }



    public City getDeparture() {
        return departure;
    }

    public City getDestination() {
        return destination;
    }

    public TicketClass getTicketClass() {
        return ticketClass;
    }

    public double getBaseTicketPrice() {
        return baseTicketPrice;
    }


}
