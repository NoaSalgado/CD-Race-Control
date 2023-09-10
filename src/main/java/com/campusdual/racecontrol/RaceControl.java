package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Input;
import com.campusdual.racecontrol.util.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.ls.LSOutput;

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

        for(Tournament tournament: this.getTournaments()){
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
                // TODO manageTournaments();
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
        int garageIndex = Utils.printAndSelectFromList(this.getGarages());
        String garageName = this.getGarages().get(garageIndex - 1).getGarageName();
        String isUserSure = Input.string("¿Seguro que deseas eliminar el garaje "
                + garageName + "? Si(s) / No(n)");
        if (isUserSure.equalsIgnoreCase("s")) {
            this.garages.remove(garageIndex - 1);
            System.out.println("El garaje " + garageName + " ha sido eliminado correctamente");
        } else {
            System.out.println("Operación caneclada");
            displayManageGaragesMenu();
        }
    }

    public void displayEditGarageMenu() {
        System.out.println("Selecciona el garaje que quieres editar: ");
        int garageIndex = Utils.printAndSelectFromList(this.getGarages());
        this.currentGarage = this.getGarages().get(garageIndex - 1);

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
        int carIndex = Utils.printAndSelectFromList(this.currentGarage.getGarageCars());
        Car carToRemove = this.currentGarage.getGarageCars().get(carIndex - 1);
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
            System.out.println("5 - Volver");

            selectedOption = Input.integer();

            switch (selectedOption) {
                case 1:
                    Utils.printFromList(this.getRaces());
                    break;
                case 2:
                    // TODO añadir carrera
                    break;
                case 3:
                    // TODO eliminar carrera
                    break;
                case 4:
                    // TODO editar carrera
                    break;
                case 5:
                    this.init();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

        } while (selectedOption != 5);


    }

}



