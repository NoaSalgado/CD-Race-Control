package com.campusdual.racecontrol;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    private String name;
    private final List<Race> races = new ArrayList<>();

    public Tournament(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Race> getRaces() {
        return races;
    }
}
