package domain;

import enumeration.City;

import java.util.ArrayList;

public class Company {
    private ArrayList<Plane> airPlanes = new ArrayList<>();

    public Company(int numberOfPlanes) {
        for (int i = 0; i < numberOfPlanes; i++) {
            Plane plane = new Plane(("PLN" + (((i + 1) * 1733) % 1135)), City.STOCKHOLM, ((int) (Math.random()) * 20) + 5);
            airPlanes.add(plane);
            Thread thread = new Thread(plane);
            thread.start();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
