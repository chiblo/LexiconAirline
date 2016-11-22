package gumpAirlines;

import domain.Company;
import flight_control.OrderGenerator;

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(new OrderGenerator()).start();
        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Company company = new Company(10);

    }
}
