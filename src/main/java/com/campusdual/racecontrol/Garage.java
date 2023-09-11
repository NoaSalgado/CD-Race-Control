package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Input;
import com.campusdual.racecontrol.util.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Garage {
    public static final String GARAGE_NAME = "garageName";
    public static final String GARAGE_CARS = "garageCars";
    private String garageName;
    private final List<Car> garageCars = new ArrayList<>();

    public Garage(){
        this.garageName = Input.string("Introduce el nombre del garage");
    }
    public Garage(String garageName) {
        this.garageName = garageName;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public List<Car> getGarageCars() {
        return garageCars;
    }

    public void registerCar(){
        Car newCar = new Car();
        newCar.setGarageName(this.getGarageName());
        this.garageCars.add(newCar);
        System.out.println("Se ha registrado correctamente el coche " + newCar.getBrand()
                + " " + newCar.getModel());
    }

    public JSONObject exportGarage(){
        JSONObject garageObject = new JSONObject();
        JSONArray garageCarsArr = new JSONArray();
        garageObject.put(Garage.GARAGE_NAME, this.getGarageName());
        for(Car car : this.getGarageCars()){
            garageCarsArr.add(car.exportCar());
        }
        garageObject.put(Garage.GARAGE_CARS, garageCarsArr);
        return garageObject;
    }

    public static Garage  importGarage(JSONObject garageObject){
        String garageName = (String) garageObject.get(Garage.GARAGE_NAME);
        JSONArray garageCarsArr = (JSONArray) garageObject.get(Garage.GARAGE_CARS);
        Garage garage = new Garage(garageName);
        if(!garageCarsArr.isEmpty()){
            for(int i = 0; i < garageCarsArr.size(); i++){
                Car car = Car.importCar((JSONObject) garageCarsArr.get(i));
                garage.getGarageCars().add(car);
            }
        }
        return garage;
    }

    @Override
    public String toString(){
        return this.getGarageName();
    }
}
