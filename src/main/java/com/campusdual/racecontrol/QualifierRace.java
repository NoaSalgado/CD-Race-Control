package com.campusdual.racecontrol;

public class QualifierRace extends Race{
    private int warmUpMinutes;
    private int durationMinutes;

    public QualifierRace(String name, String type, int warmUpMinutes) {
        super(name, type);
        this.warmUpMinutes = warmUpMinutes;
    }

    @Override
    public void startRace() {
        this.setParticipatingCars();
        this.durationMinutes = this.getCompetingCars().size();
    }
}
