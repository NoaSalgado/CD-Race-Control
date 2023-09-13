package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Input;
import com.campusdual.racecontrol.util.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public abstract class Race {
    public static final String RACE_NAME = "raceName";
    public static final String TYPE = "raceType";
    public static final String PARTICIPATING_GARAGES = "participatingGarages";
    public static final String COMPETING_CARS = "competingCars";
    private String raceName;
    private String raceType;
    private final List<Garage> participatingGarages = new ArrayList<>();
    private final List<Car> competingCars = new ArrayList<>();
    private final List<Car> podium = new ArrayList<>();

    public Race(){
        this.raceName = Input.string("Introduce el nombre de la carrera: ");
    }
    public Race(String raceName, String raceType){
        this.raceName = raceName;
        this.raceType = raceType;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getRaceType() {
        return raceType;
    }

    public void setRaceType(String raceType) {
        this.raceType = raceType;
    }

    public List<Garage> getParticipatingGarages() {
        return participatingGarages;
    }

    public List<Car> getCompetingCars() {
        return competingCars;
    }

    public List<Car> getPodium() {
        return podium;
    }

    public void setParticipatingCars(){
       if(!this.getParticipatingGarages().isEmpty()){
           if(this.getParticipatingGarages().size() == 1){
               this.competingCars.addAll(this.getParticipatingGarages().get(0).getGarageCars());
           }else{
               for(Garage garage: this.getParticipatingGarages()){
                   int randomCarIndex = Utils.generateRandomNumber(0, garage.getGarageCars().size());
                   this.getCompetingCars().add(garage.getGarageCars().get(randomCarIndex));
               }
           }
       }
    }

    public abstract void startRace();
    public void getRaceRanking(){
        Collections.sort(this.getCompetingCars());
        Collections.reverse(this.getCompetingCars());
    }

    public void checkPodium(){
        for(int i = 0; i < 3; i++){
            this.podium.add(this.competingCars.get(i));
        }
    }

    public void printPodium(){
        for(int i = 0; i < this.podium.size(); i++){
            System.out.println("Podición: " + (i+1) + "º");
            System.out.println(this.getPodium().get(i));
        }
    }
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
}
