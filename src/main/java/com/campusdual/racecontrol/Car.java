package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Input;
import com.campusdual.racecontrol.util.Utils;
import org.json.simple.JSONObject;

import java.util.Random;

public class Car implements Comparable<Car> {
    public static final String BRAND = "brand";
    public static final String MODEL = "model";
    public static final String GARAGE = "garage";
    private String brand;
    private String model;
    private String garageName = "";
    public static final int MAX_SPEED = 200;
    private int speed = 0;
    private double distanceCovered = 0;
    private int points = 0;

    public Car(){
        this.brand = Input.string("Introduce la marca: ");
        this.model = Input.string("Introduce el modelo: ");
    }

    public Car(String brand, String model){
        this.brand = brand;
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getGarageName() {
        return garageName;
    }

    public int getSpeed() {
        return speed;
    }

    public double getDistanceCovered() {
        return distanceCovered;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    private void speedUp(){
        if(this.speed < Car.MAX_SPEED){
            this.speed += 10;
        }
    }

    private void slowDown(){
        if(this.speed > 0){
            this.speed -= 10;
        }
    }
    private void updateDistance(){
        this.distanceCovered += speed * 16.667;
    }

    public void updateSpeedByCycle(){
        int action = Utils.generateRandomNumber(1, 3);
        if(action != 2){
            speedUp();
        }else{
            slowDown();
        }
        updateDistance();
    }

    public void resetCar(){
        this.speed= 0;
        this.distanceCovered = 0;
    }

    public JSONObject exportCar(){
        JSONObject carObject = new JSONObject();
        carObject.put(Car.BRAND, this.getBrand());
        carObject.put(Car.MODEL, this.getModel());
        carObject.put(Car.GARAGE, this.getGarageName());
        return carObject;
    }

    public static Car importCar(JSONObject carObject){
        String brand = (String) carObject.get(Car.BRAND);
        String model = (String) carObject.get(Car.MODEL);
        String garage = (String) carObject.get(Car.GARAGE);
        Car car = new Car(brand, model);
        if(!garage.isEmpty()){
            car.setGarageName(garage);
        }
        return car;
    }

    @Override
    public int compareTo(Car o) {
        if(this.getDistanceCovered() > o.getDistanceCovered()){
            return 1;
        }else if(this.getDistanceCovered() < o.getDistanceCovered()){
            return -1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\tMarca: ").append(this.getBrand());
        sb.append("\n\tModelo: ").append(this.getModel());
        sb.append("\n\tGaraje: ").append(this.getGarageName());
        sb.append("\n\tDistancia recorrida: ").append(this.getDistanceCovered());
        sb.append("\n\tPuntos: ").append(this.getPoints());
        sb.append("\n         ######"         );
        return sb.toString();
    }
}

