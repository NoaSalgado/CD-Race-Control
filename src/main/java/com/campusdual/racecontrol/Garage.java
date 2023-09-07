package com.campusdual.racecontrol;

import java.util.ArrayList;
import java.util.List;

public class Garage {
    private String garageName;
    private final List<Car> garageCars = new ArrayList<>();

    public Garage(String garageName) {
        this.garageName = garageName;
    }

    public String getGarageName() {
        return garageName;
    }

    public List<Car> getGarageCars() {
        return garageCars;
    }
}
