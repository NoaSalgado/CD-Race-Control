package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Input;
import com.campusdual.racecontrol.util.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaceControl {
    public static final String GARAGES = "garages";
    public static final String RACES = "races";
    public static final String TOURNAMENTS = "tournaments";
    private final List<Garage> garages = new ArrayList<>();
    private final List<Race> races = new ArrayList<>();
    private final List<Tournament> tournaments = new ArrayList<>();
    private Garage currentGarage = null;
    private Race currentRace = null;
    private Tournament currentTournament = null;

    public List<Garage> getGarages() {
        return garages;
    }

    public List<Race> getRaces() {
        return races;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public static void main(String[] args) {
        RaceControl myRaceControl = new RaceControl();
        myRaceControl.init();
    }

    private void saveData() {
        JSONObject appData = new JSONObject();
        JSONArray appGarages = new JSONArray();
        JSONArray appRaces = new JSONArray();
        JSONArray appTournaments = new JSONArray();

        for (Garage garage : this.getGarages()) {
            appGarages.add(garage.exportGarage());
        }

        for (Race race : this.getRaces()) {
            appRaces.add(race.exportRace());
        }

        for (Tournament tournament : this.getTournaments()) {
            appTournaments.add(tournament.exportTournament());
        }

        appData.put(RaceControl.GARAGES, appGarages);
        appData.put(RaceControl.RACES, appRaces);
        appData.put(RaceControl.TOURNAMENTS, appTournaments);

        try (FileWriter file = new FileWriter("data.json")) {
            file.write(appData.toJSONString());
        } catch (IOException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void importData() {
        JSONObject appData = null;
        try (FileReader r = new FileReader("data.json")) {
            JSONParser parser = new JSONParser();
            appData = (JSONObject) parser.parse(r);
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        JSONArray appGarages = (JSONArray) appData.get(RaceControl.GARAGES);
        for (int i = 0; i < appGarages.size(); i++) {
            Garage garage = Garage.importGarage((JSONObject) appGarages.get(i));
            this.getGarages().add(garage);
        }

        JSONArray appRaces = (JSONArray) appData.get(RaceControl.RACES);
        for (int i = 0; i < appRaces.size(); i++) {
            JSONObject raceObj = (JSONObject) appRaces.get(i);
            Race race = null;
            if (raceObj.get(Race.TYPE).equals("Standard")) {
                race = StandardRace.importRace(raceObj);
            } else {
                race = QualifierRace.importRace(raceObj);
            }
            this.getRaces().add(race);
        }

        JSONArray appTournaments = (JSONArray) appData.get(RaceControl.TOURNAMENTS);
        for (int i = 0; i < appTournaments.size(); i++) {
            Tournament tournament = Tournament.importTournament((JSONObject) appTournaments.get(i));
            this.getTournaments().add(tournament);
        }
    }


    public void init() {
        this.importData();
        System.out.println("********************************");
        System.out.println("** Bienvenid@ a Race Control! **");
        System.out.println("********************************");
        System.out.println("Selecciona la acción que deseas realizar: ");
        System.out.println("1 - Gestionar garajes");
        System.out.println("2 - Gestionar carreras");
        System.out.println("3 - Gestionar torneos");
        System.out.println("4 - Salir");
        int selectedOption = Input.integer();

        switch (selectedOption) {
            case 1:
                this.displayManageGaragesMenu();
                break;
            case 2:
                this.displayManageRacesMenu();
                break;
            case 3:
                this.displayManageTournamentsMenu();
                break;
            case 4:
                System.out.println("Has cerrado la aplicación");
                this.saveData();
                break;
            default:
                System.out.println("Opción incorrecta");
                init();
        }
    }

    public void displayManageGaragesMenu() {
        int selectedOption;
        do {
            System.out.println("Selecciona la acción que deseas realizar: ");
            System.out.println("1 - Ver garajes");
            System.out.println("2 - Añadir garaje");
            System.out.println("3 - Eliminar garaje");
            System.out.println("4 - Editar garaje");
            System.out.println("5 - Volver");
            selectedOption = Input.integer();

            switch (selectedOption) {
                case 1:
                    Utils.printFromList(this.getGarages());
                    break;
                case 2:
                    this.addGarage();
                    break;
                case 3:
                    this.removeGarage();
                    break;
                case 4:
                    this.displayEditGarageMenu();
                    break;
                case 5:
                    this.currentGarage = null;
                    this.init();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (selectedOption != 5);
    }

    public void addGarage() {
        this.garages.add(new Garage());
        System.out.println("Garage añadido correctamente");
    }

    public void removeGarage() {
        System.out.println("Selecciona el garaje que deseas eliminar: ");
        Garage garage = Utils.printAndSelectFromList(this.getGarages());
        String isUserSure = Input.string("¿Seguro que deseas eliminar el garaje "
                + garage.getGarageName() + "? Si(s) / No(n)");
        if (isUserSure.equalsIgnoreCase("s")) {
            this.garages.remove(garage);
            System.out.println("El garaje " + garage.getGarageName() + " ha sido eliminado correctamente");
        } else {
            System.out.println("Operación caneclada");
            displayManageGaragesMenu();
        }
    }

    public void displayEditGarageMenu() {
        System.out.println("Selecciona el garaje que quieres editar: ");
        this.currentGarage = Utils.printAndSelectFromList(this.getGarages());

        int selectedOption;
        do {
            System.out.println("Selecciona la acción que deseas realizar: ");
            System.out.println("1 - Ver coches");
            System.out.println("2 - Registrar un coche");
            System.out.println("3 - Eliminar un coche");
            System.out.println("4 - Cambiar nombre");
            System.out.println("5 - Volver");
            selectedOption = Input.integer();

            switch (selectedOption) {
                case 1:
                    Utils.printFromList(this.currentGarage.getGarageCars());
                    break;
                case 2:
                    this.currentGarage.registerCar();
                    break;
                case 3:
                    this.removeCarFromGarage();
                    break;
                case 4:
                    this.changeGarageName();
                    break;
                case 5:
                    this.currentGarage = null;
                    this.displayManageGaragesMenu();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (selectedOption != 5);
    }

    public void removeCarFromGarage() {
        System.out.println("Selecciona el coche que deseas eliminar: ");
        Car carToRemove = Utils.printAndSelectFromList(this.currentGarage.getGarageCars());
        String isUserSure = Input.string("¿Seguro que quieres eliminar el coche " +
                carToRemove.getBrand() + " " + carToRemove.getModel() + "? Si(s) / No(n)");
        if (isUserSure.equalsIgnoreCase("s")) {
            this.currentGarage.getGarageCars().remove(carToRemove);
            System.out.println("Se ha eliminado correctamente el coche "
                    + carToRemove.getBrand() + " " + carToRemove.getModel());
        } else {
            System.out.println("Operación cancelada");
            this.displayEditGarageMenu();
        }
    }

    public void changeGarageName() {
        String actualGarageName = this.currentGarage.getGarageName();
        String newGarageName = Input.string("Introduce el nuevo nombre del garage: ");
        this.currentGarage.setGarageName(newGarageName);
        System.out.println("El garaje " + actualGarageName + " ha cambiado su nombre a " +
                newGarageName);
    }

    public void displayManageRacesMenu() {
        int selectedOption;
        do {
            System.out.println("Selecciona la acción que deseas realizar: ");
            System.out.println("1 - Ver carreras");
            System.out.println("2 - Añadir carrera");
            System.out.println("3 - Eliminar carrera");
            System.out.println("4 - Editar carrera");
            System.out.println("5 - Iniciar una carrera");
            System.out.println("6 - Volver");

            selectedOption = Input.integer();

            switch (selectedOption) {
                case 1:
                    Utils.printFromList(this.getRaces());
                    break;
                case 2:
                    this.addRace();
                    break;
                case 3:
                    this.removeRace();
                    break;
                case 4:
                    this.displayEditRaceMenu();
                    break;
                case 5:
                    this.runRace();
                    break;
                case 6:
                    this.init();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

        } while (selectedOption != 6);
    }

    public void addRace() {
        Race newRace = null;
        System.out.println("Selecciona el tipo de carrera que deseas añadir: ");
        System.out.println("1 - Estándar");
        System.out.println("2 - Eliminatoria");
        int typeSelection = Input.integer();
        if (typeSelection != 1 && typeSelection != 2) {
            System.out.println("Opción no válida. Operación cancelada");
            this.displayManageRacesMenu();
        } else if (typeSelection == 1) {
            newRace = new StandardRace();
            newRace.setRaceType("Standard");
        } else {
            newRace = new QualifierRace();
            newRace.setRaceType("Qualifier");
        }
        this.getRaces().add(newRace);
        System.out.println("Se ha creado la carrera " + newRace.getRaceName());
    }

    public void removeRace() {
        System.out.println("Selecciona la carrera que deseas eliminar: ");
        Race raceToRemove = Utils.printAndSelectFromList(this.getRaces());
        String isUserSure = Input.string("¿Seguro que deseas eliminar la carrera "
                + raceToRemove.getRaceName() + "? Si(s) / No(n)");
        if (isUserSure.equalsIgnoreCase("s")) {
            this.getRaces().remove(raceToRemove);
            System.out.println("La carrera " + raceToRemove.getRaceName() + " ha sido eliminada correctamente");
        } else {
            System.out.println("Operación caneclada");
            this.displayManageRacesMenu();
        }
    }

    public void displayEditRaceMenu() {
        System.out.println("Selecciona la carrera que quieres editar: ");
        this.currentRace = Utils.printAndSelectFromList(this.getRaces());

        int selectedOption;
        do {
            System.out.println("Selecciona la acción que deseas realizar: ");
            System.out.println("1 - Ver garages participantes");
            System.out.println("2 - Ver coches participantes");
            System.out.println("3 - Añadir garage");
            System.out.println("4 - Eliminar garage");
            System.out.println("5 - Cambiar nombre");
            System.out.println("6 - Ver podiums");
            System.out.println("7 - Volver");
            selectedOption = Input.integer();

            switch (selectedOption) {
                case 1:
                    Utils.printFromList(this.currentRace.getParticipatingGarages());
                    break;
                case 2:
                    Utils.printFromList(currentRace.getCompetingCars());
                    break;
                case 3:
                    this.addGarageToRace();
                    break;
                case 4:
                    this.removeGarageFromRace();
                    break;
                case 5:
                    this.changeRaceName();
                    break;
                case 6:
                    // TODO ver podiums
                    break;
                case 7:
                    this.displayManageRacesMenu();
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (selectedOption != 7);
    }

    public void runRace(){
        System.out.println("Elige la carrera que quieres iniciar: ");
        this.currentRace = Utils.printAndSelectFromList(this.getRaces());
        this.currentRace.startRace();
        System.out.println("Carrera finalizada");
        System.out.println("El podium de la carrera es: ");
        this.currentRace.printPodium();
    }
    public void addGarageToRace() {
        System.out.println("Selecciona el garaje que quieres añadir: ");
        Garage garageToAdd = Utils.printAndSelectFromList(this.getGarages());
        // TODO comprobar si el garaje ya está en la lista de participantes
        this.currentRace.getParticipatingGarages().add(garageToAdd);
        System.out.println("Se ha añadido el garaje " + garageToAdd.getGarageName() + " a la carrera");
    }

    public void removeGarageFromRace(){
        System.out.println("Selecciona el garaje que quieres eliminar: ");
        Garage garageToRemove = Utils.printAndSelectFromList(this.currentRace.getParticipatingGarages());
        String isUserSure = Input.string("¿Seguro que deseas eliminar el garaje "
                + garageToRemove.getGarageName() + "? Si(s) / No(n)");
        if (isUserSure.equalsIgnoreCase("s")) {
            this.currentRace.getParticipatingGarages().remove(garageToRemove);
            System.out.println("El garaje " + garageToRemove.getGarageName() + " ha sido eliminado correctamente");
        } else {
            System.out.println("Operación caneclada");
            this.displayEditRaceMenu();
        }
    }

    public void changeRaceName() {
        String actualRaceName = this.currentRace.getRaceName();
        String newRaceName = Input.string("Introduce el nuevo nombre de la carrera: ");
        this.currentRace.setRaceName(newRaceName);
        System.out.println("La carrera " + actualRaceName + " ha cambiado su nombre a " +
                newRaceName);
    }

    public void displayManageTournamentsMenu(){
        System.out.println("Selecciona la acción que deseas realizar: ");
        int selectedOption;

        do{
            System.out.println("1 - Ver torneos");
            System.out.println("2 - Añadir torneo");
            System.out.println("3 - Eliminar torneo");
            System.out.println("4 - Editar torneo");
            System.out.println("5 - Iniciar torneo");
            System.out.println("6 - Volver");
            selectedOption = Input.integer();

            switch(selectedOption){
                case 1:
                    Utils.printFromList(this.getTournaments());
                    break;
                case 2:
                    this.addTournament();
                    break;
                case 3:
                    this.removeTournament();
                    break;
                case 4:
                    this.displayEditTournamentMenu();
                    break;
                case 5:
                    this.startTournament();
                    Utils.printFromList(currentTournament.getTournamentCars());
                    break;
                case 6:
                    this.currentTournament = null;
                    this.init();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }while (selectedOption != 6);
    }

    public void addTournament(){
        Tournament newTournament = new Tournament();
        this.getTournaments().add(newTournament);
        System.out.println("Se ha creado el torneo " + newTournament.getTournamentName());
    }

    public void removeTournament(){
        System.out.println("Selecciona el torneo que deseas eliminar: ");
        Tournament tournamentToRemove = Utils.printAndSelectFromList(this.getTournaments());
        String isUserSure = Input.string("¿Seguro que deseas eliminar el garaje "
                + tournamentToRemove.getTournamentName() + "? Si(s) / No(n)");
        if (isUserSure.equalsIgnoreCase("s")) {
            this.getTournaments().remove(tournamentToRemove);
            System.out.println("El torneo " + tournamentToRemove.getTournamentName() + " ha sido eliminado correctamente");
        } else {
            System.out.println("Operación caneclada");
            this.displayManageTournamentsMenu();
        }
    }

    public void startTournament(){
        System.out.println("Selecciona el torneo que quieres iniciar: ");
        this.currentTournament = Utils.printAndSelectFromList(this.getTournaments());
        this.currentTournament.startTournament();
    }

    public void displayEditTournamentMenu(){
        System.out.println("Selecciona el torneo que deseas editar: ");
        this.currentTournament = Utils.printAndSelectFromList(this.getTournaments());
        int selectedOption;

        do{
            System.out.println("Selecciona la acción que deseas realizar: ");
            System.out.println("1 - Ver garajes participantes");
            System.out.println("2 - Ver carreras");
            System.out.println("3 - Añadir garaje");
            System.out.println("4 - Eliminar garaje");
            System.out.println("5 - Añadir carrera");
            System.out.println("6 - Eliminar carrera");
            System.out.println("7 - Cambiar nombre");
            System.out.println("8 - Volver");
            selectedOption = Input.integer();

            switch(selectedOption){
                case 1:
                    Utils.printFromList(this.currentTournament.getTournamentsGarages());
                    break;
                case 2:
                    Utils.printFromList(this.currentTournament.getTournamentRaces());
                    break;
                case 3:
                    this.addGarageToTournament();
                    break;
                case 4:
                    this.removeGarageFromTournament();
                    break;
                case 5:
                    this.addRaceToTournament();
                    break;
                case 6:
                    this.removeRaceFromTournament();
                    break;
                case 7:
                 // TODO cambiar nombre
                    break;
                case 8:
                    this.currentTournament = null;
                    this.displayManageTournamentsMenu();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }while(selectedOption != 7);
    }

    public void addGarageToTournament(){
        System.out.println("Selecciona el garaje que deseas añadir: ");
        Garage garageToAdd = Utils.printAndSelectFromList(this.getGarages());
        this.currentTournament.getTournamentsGarages().add(garageToAdd);
        System.out.println("Has añadido el garaje " + garageToAdd.getGarageName());
    }

    public void removeGarageFromTournament(){
        System.out.println("Selecciona el garaje que deseas eliminar");
        Garage garageToTemove = Utils.printAndSelectFromList(this.currentTournament.getTournamentsGarages());
        String isUserSure = Input.string("¿Seguro que deseas eliminar el garaje "
                + garageToTemove.getGarageName() + "? Si(s) / No(n)");
        if (isUserSure.equalsIgnoreCase("s")) {
            this.currentTournament.getTournamentsGarages().remove(garageToTemove);
            System.out.println("El garaje " + garageToTemove.getGarageName() + " ha sido eliminado correctamente");
        } else {
            System.out.println("Operación caneclada");
            this.displayEditTournamentMenu();
        }
    }

    public void addRaceToTournament(){
        System.out.println("Selecciona la carrera que deseas añadir: ");
        Race  raceToAdd = Utils.printAndSelectFromList(this.getRaces());
        this.currentTournament.getTournamentRaces().add(raceToAdd);
        System.out.println("Has añadido la carrera " + raceToAdd.getRaceName());
    }

    public void removeRaceFromTournament(){
        System.out.println("Selecciona la carrera que deseas eliminar");
        Race raceToTemove = Utils.printAndSelectFromList(this.currentTournament.getTournamentRaces());
        String isUserSure = Input.string("¿Seguro que deseas eliminar el garaje "
                +raceToTemove.getRaceName() + "? Si(s) / No(n)");
        if (isUserSure.equalsIgnoreCase("s")) {
            this.currentTournament.getTournamentsGarages().remove(raceToTemove);
            System.out.println("La carrera " + raceToTemove.getRaceName() + " ha sido eliminada correctamente");
        } else {
            System.out.println("Operación caneclada");
            this.displayEditTournamentMenu();
        }
    }
}




