package com.campusdual.racecontrol.util;

import com.campusdual.racecontrol.Car;

import java.util.Comparator;

public class CompareCarByPoints implements Comparator<Car> {
    @Override
    public int compare(Car o1, Car o2) {
        if(o1.getPoints() > o2.getPoints()){
            return 1;
        }else if(o1.getPoints() < o2.getPoints()){
            return -1;
        }else{
            return 0;
        }
    }
}
