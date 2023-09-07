package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Race {
    private String name;
    private String type;
    private final List<Garage> participatingGarages = new ArrayList<>();
    private final List<Car> competingCars = new ArrayList<>();
    private final Map<String, Car> podium = new HashMap<>();

    public Race(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
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
                int randomCarIndex = Utils.generateRandomNumber(garage.getGarageCars().size());
                this.getCompetingCars().add(garage.getGarageCars().get(randomCarIndex));
            }
        }
    }

    public abstract void startRace();
}
