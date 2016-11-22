package enumeration;

import domain.Passenger;

/**
 * Created by User on 2016-07-14.
 */
public class FoodPrice {


    public static double getFoodPrice(Passenger passenger) {
        if (passenger.getTicketClass() == TicketClass.ECONOMY_CLASS) {
            switch (passenger.getMeal()) {
                case MEAL_PACKAGE_1:
                    return 100.0;
                case MEAL_PACKAGE_2:
                    return 120.0;
                case MEAL_PACKAGE_3:
                    return 180.0;
                case NO_MEAL:
                    return 0.0;
            }

        } else {
            switch (passenger.getMeal()) {
                case MEAL_PACKAGE_1:
                    return 300.0;
                case MEAL_PACKAGE_2:
                    return 450.0;
                case MEAL_PACKAGE_3:
                    return 800.0;
                case NO_MEAL:
                    return 0.0;
            }
        }
        return 0.0;
    }
}