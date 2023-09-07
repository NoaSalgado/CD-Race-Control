package com.campusdual.racecontrol;

public class StandardRace extends Race{
    private int duration;

    public StandardRace(String name, String type, int duration) {
        super(name, type);
        this.duration = duration;
    }

    public StandardRace(String name, String type){
        super(name, type);
        this.duration = 3;
    }

    @Override
    public void startRace() {

    }
}
