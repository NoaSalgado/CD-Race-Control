package com.campusdual.racecontrol;

import org.example.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    public static final String TOURNAMENT_NAME = "tournamentName";
    public static final String TOURNAMENT_RACES = "tournamentRaces";
    private String tournamentName;
    private final List<Race> tournamentRaces = new ArrayList<>();

    public Tournament(){
        this.tournamentName = Input.string("Introduce el nombre del torneo: ");
    }

    public Tournament(String tournamentName){
        this.tournamentName = tournamentName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public List<Race> getTournamentRaces() {
        return tournamentRaces;
    }

    public JSONObject exportTournament(){
        JSONObject tournamentObject = new JSONObject();
        JSONArray tournamentRacesArr = new JSONArray();
        for(Race race : this.getTournamentRaces()){
            tournamentRacesArr.add(race.exportRace());
        }
        tournamentObject.put(Tournament.TOURNAMENT_NAME, this.getTournamentName());
        tournamentObject.put(Tournament.TOURNAMENT_RACES, tournamentRacesArr);
        return tournamentObject;
    }

    public static Tournament importTournament(JSONObject tournamentObject){
        String tournamentName = (String) tournamentObject.get(Tournament.TOURNAMENT_NAME);
        JSONArray tournamentRacesArr = (JSONArray) tournamentObject.get(Tournament.TOURNAMENT_RACES);
        Tournament tournament = new Tournament(tournamentName);

        for(int i = 0; i < tournamentRacesArr.size(); i++){
            JSONObject raceObj = (JSONObject) tournamentRacesArr.get(i);
            Race race = null;
            if (raceObj.get(Race.TYPE).equals("Standard")) {
                race = StandardRace.importRace(raceObj);
            } else {
                race = QualifierRace.importRace(raceObj);
            }
            tournament.getTournamentRaces().add(race);
        }
        return tournament;
    }
    @Override
    public String toString(){
        return this.getTournamentName();
    }
}
