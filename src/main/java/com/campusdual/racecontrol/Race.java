package com.campusdual.racecontrol;

import org.example.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Race {
    public static final String RACE_NAME = "raceName";
    public static final String TYPE = "raceType";
    public static final String PARTICIPATING_GARAGES = "participatingGarages";
    public static final String COMPETING_CARS = "competingCars";
    private String raceName;
    private String raceType;
    private final List<Garage> participatingGarages = new ArrayList<>();
    private final List<Car> competingCars = new ArrayList<>();
    private final Map<String, Car> podium = new HashMap<>();

    public Race(String raceType){
        this.raceName = Input.string("Introduce el nombre de la carrera: ");
        this.raceType = raceType;
    }
    public Race(String raceName, String raceType){
        this.raceName = raceName;
        this.raceType = raceType;
    }

    public String getRaceName() {
        return raceName;
    }

    public String getRaceType() {
        return raceType;
    }

    public List<Garage> getParticipatingGarages() {
        return participatingGarages;
    }

    public List<Car> getCompetingCars() {
        return competingCars;
    }

    public Map<String, Car> getPodium() {
        return podium;
    }

    public void setParticipatingCars(){
        if(this.getParticipatingGarages().size() == 1){
            this.competingCars.addAll(this.getParticipatingGarages().get(0).getGarageCars());
        }else{
            for(Garage garage: this.getParticipatingGarages()){
                //int randomCarIndex = Utils.generateRandomNumber(garage.getGarageCars().size());
                //this.getCompetingCars().add(garage.getGarageCars().get(randomCarIndex));
            }
        }
    }
    public abstract void startRace();
    public abstract  JSONObject exportRace();

    protected JSONArray exportParticipatingGarages(){
        JSONArray raceGaragesArr = new JSONArray();
        for(Garage garage: this.getParticipatingGarages()){
            raceGaragesArr.add(garage.exportGarage());
        }
        return raceGaragesArr;
    }

    protected void importGarages(JSONArray garagesArr){
        for(int i = 0; i < garagesArr.size(); i++){
            Garage garage = Garage.importGarage((JSONObject) garagesArr.get(i));
            this.getParticipatingGarages().add(garage);
        }
    }

    protected JSONArray exportParticipatingCars(){
        JSONArray raceCarsArr = new JSONArray();
        for(Car car: this.getCompetingCars()){
            raceCarsArr.add(car.exportCar());
        }
        return raceCarsArr;
    }

    protected void importCars(JSONArray carsArr){
        for(int i = 0; i < carsArr.size(); i++){
            Car car = Car.importCar((JSONObject) carsArr.get(i));
            this.getCompetingCars().add(car);
        }
    }

    @Override
    public String toString(){
        return this.getRaceName();
    }
}
