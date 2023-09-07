package com.campusdual.racecontrol;

import java.util.Random;

public class Car {
    private  String brand;
    private  String model;
    private static final int MAX_SPEED = 250;
    private int speedInterval;
    private int speed;
    private int distanceCovered;
    private int points;

    public Car(String brand, String model, int speedInterval){
        this.brand = brand;
        this.model = model;
        this.speedInterval = speedInterval;
        this.speed = 0;
        this.distanceCovered = 0;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getSpeedInterval(){
        return this.speedInterval;
    }

    public int getSpeed(){
        return this.speed;
    }

    public int getDistanceCovered(){
        return this.distanceCovered;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void runTheRace(){
        Random rand = new Random();
        int action = rand.nextInt();
        if(action == 0){
            this.slowDown();
        }else{
            this.speedUp();
        }
        calculateDistanceCovered();
    }

    private void speedUp(){
        if(this.speed + this.getSpeedInterval() < MAX_SPEED){
            this.speed += this.getSpeedInterval();
        }else{
            this.speed = MAX_SPEED;
        }
    }

    private void slowDown(){
        if(this.speed - this.getSpeedInterval() > 0){
            this.speed -= this.getSpeedInterval();
        }else{
            this.speed = 0;
        }
    }

    private void calculateDistanceCovered(){
        this.distanceCovered += speed / 60;
    }
}
