package com.campusdual.racecontrol;

import org.example.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class QualifierRace extends Race{
    public static final String WARMUP_MINUTES = "warmUpMinutes";
    public static final String RACE_TYPE = "Qualifier";
    private int warmUpMinutes;

    public QualifierRace(String raceName, String raceType) {
        super(raceName, raceType);
        this.warmUpMinutes = Input.integer("Introduce la duración en minutos del calentamiento");
    }

    public QualifierRace(String raceName, String raceType, int warmUpMinutes){
        super(raceName, raceType);
        this.warmUpMinutes = warmUpMinutes;
    }

    public int getWarmUpMinutes() {
        return warmUpMinutes;
    }

    public void setWarmUpMinutes(int warmUpMinutes) {
        this.warmUpMinutes = warmUpMinutes;
    }

    @Override
    public void startRace() {

    }

    @Override
    public JSONObject exportRace() {
        JSONObject raceObject = new JSONObject();
        JSONArray garagesArr = this.exportParticipatingGarages();
        JSONArray carsArr = this.exportParticipatingCars();

        raceObject.put(Race.RACE_NAME, this.getRaceName());
        raceObject.put(Race.TYPE, this.getRaceType());
        raceObject.put(QualifierRace.WARMUP_MINUTES, this.getWarmUpMinutes());
        raceObject.put(Race.PARTICIPATING_GARAGES, garagesArr);
        raceObject.put(Race.COMPETING_CARS, carsArr);
        return raceObject;
    }

    public static QualifierRace importRace(JSONObject raceObject) {
        String raceName = (String) raceObject.get(Race.RACE_NAME);
        String raceType = (String) raceObject.get(Race.TYPE);
        int warmUpMinutes = (int)(long) raceObject.get(QualifierRace.WARMUP_MINUTES);
        JSONArray garagesArr = (JSONArray) raceObject.get(Race.PARTICIPATING_GARAGES);
        JSONArray carsArr = (JSONArray) raceObject.get(Race.COMPETING_CARS);

        QualifierRace race = new QualifierRace(raceName, raceType, warmUpMinutes);
        race.importGarages(garagesArr);
        race.importCars(carsArr);
        return race;
    }
}
