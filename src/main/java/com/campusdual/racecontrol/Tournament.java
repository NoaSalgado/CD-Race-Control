package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.CompareCarByPoints;
import com.campusdual.racecontrol.util.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tournament {
    public static final String TOURNAMENT_NAME = "tournamentName";
    public static final String TOURNAMENT_RACES = "tournamentRaces";
    private String tournamentName;
    private final List<Garage> tournamentsGarages = new ArrayList<>();
    private final List<Race> tournamentRaces = new ArrayList<>();
    private final List<Car> tournamentCars = new ArrayList<>();
    private Car winner = null;

    public Tournament(){
        this.tournamentName = Input.string("Introduce el nombre del torneo: ");
    }

    public Tournament(String tournamentName){
        this.tournamentName = tournamentName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public List<Garage> getTournamentsGarages() {
        return tournamentsGarages;
    }

    public List<Race> getTournamentRaces() {
        return tournamentRaces;
    }

    public List<Car> getTournamentCars() {
        return tournamentCars;
    }

    public Car getWinner() {
        return winner;
    }

    public void startTournament() {
        for (Race race : this.getTournamentRaces()) {
            race.getParticipatingGarages().clear();
            for(Garage garage : this.getTournamentsGarages()){
                race.getParticipatingGarages().add(garage);
            }
           race.startRace();
            int points = 3;
            for(int i = 0; i < race.getPodium().size(); i++){
                race.getPodium().get(i).setPoints(points);
                points--;
            }
            this.getTournamentCars().addAll(race.getCompetingCars());
        }
        this.checkWinner();
        this.printWinner();
    }

    public void checkWinner(){
        Collections.sort(this.getTournamentCars(), new CompareCarByPoints());
        Collections.reverse(this.getTournamentCars());
        this.winner = this.getTournamentCars().get(0);
    }

    public void printWinner(){
        System.out.println("El ganador del torneo es " + this.getWinner());
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
