package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StandardRace extends Race{
    public static final String MINS_DURATION = "minsDuration";
    private int minsDuration = 180;

    public StandardRace(){

    }
    public StandardRace(String raceName, String raceType) {
        super(raceName, raceType);
    }

    public int getMinsDuration() {
        return minsDuration;
    }

    public void setMinsDuration(){
        this.minsDuration = Input.integer("Introduce la duración de la carrera en minutos: ");
    }
    public void setMinsDuration(int minsDuration) {
        this.minsDuration = minsDuration;
    }

    @Override
    public void startRace() {
        this.setParticipatingCars();
        int timeRemainig = this.minsDuration;
        while(timeRemainig > 0){
            for(Car car: this.getCompetingCars()){
                car.updateSpeedByCycle();
            }
            timeRemainig--;
        }
        this.getRaceRanking();
        this.checkPodium();
    }

    @Override
    public JSONObject exportRace() {
        JSONObject raceObject = new JSONObject();
        JSONArray garagesArr = this.exportParticipatingGarages();
        JSONArray carsArr = this.exportParticipatingCars();

        raceObject.put(Race.RACE_NAME, this.getRaceName());
        raceObject.put(Race.TYPE, this.getRaceType());
        raceObject.put(StandardRace.MINS_DURATION, this.getMinsDuration());
        raceObject.put(Race.PARTICIPATING_GARAGES, garagesArr);
        raceObject.put(Race.COMPETING_CARS, carsArr);
        return raceObject;
    }

    public static Race importRace(JSONObject raceObject) {
        String raceName = (String) raceObject.get(Race.RACE_NAME);
        String raceType = (String) raceObject.get(Race.TYPE);
        int minsDuration = (int)(long) raceObject.get(StandardRace.MINS_DURATION);
        JSONArray garagesArr = (JSONArray) raceObject.get(Race.PARTICIPATING_GARAGES);
        JSONArray carsArr = (JSONArray) raceObject.get(Race.COMPETING_CARS);

        StandardRace race = new StandardRace(raceName, raceType);
        if(minsDuration != 180){
            race.setMinsDuration((int) minsDuration);
        }

        race.importGarages(garagesArr);
        race.importCars(carsArr);

        return race;
    }
}
