package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceControl {
    private final List<Tournament> tournaments = new ArrayList<>();
    public static void main(String[] args) {
        RaceControl myRaceControl = new RaceControl();
    }

    public void populateRaceControl(){
        Car citroenXara = new Car("Citroen", "Xara", 10);
        Car fordMondeo = new Car("Ford", "Mondeo", 11);
        Car peugeot207 = new Car("Peugeot", "207", 10);
        Car toyotaAuris = new Car("Toyota", "Auris", 13);
        Car toyotaPirus = new Car("Toyota", "Prius", 13);
        Car seatLeon = new Car("Seat", "Leon", 12);
        Car renaultMegane = new Car("Renault", "Megane", 12);
        Car seatIbiza = new Car("Seat", "Ibiza", 9);
        Car renaultClio = new Car("Renault", "Clio", 9);
        Car peugeot208 = new Car("Peugeot", "208", 11);
        Car audiA3 = new Car("Audi", "A3", 15);
        Car citroenC3 = new Car("Citroen", "C3", 11);
        Car fordFiesta = new Car("Ford", "Fiesta", 10);
        Car fiatStilo = new Car("Fiat", "Stilo", 12);
        Car hondaCivic = new Car("Honda", "Civic", 13);

        Garage crazyCars = new Garage("Crazy Cars");
        crazyCars.getGarageCars().add(citroenXara);
        crazyCars.getGarageCars().add(fordMondeo);
        crazyCars.getGarageCars().add(peugeot207);
        crazyCars.getGarageCars().add(toyotaAuris);
        crazyCars.getGarageCars().add(toyotaPirus);

        Garage fabulousCars = new Garage("Fabulous Cars");
        fabulousCars.getGarageCars().add(seatLeon);
        fabulousCars.getGarageCars().add(renaultMegane);
        fabulousCars.getGarageCars().add(seatIbiza);
        fabulousCars.getGarageCars().add(renaultClio);
        fabulousCars.getGarageCars().add(peugeot208);

        Garage funnyCars = new Garage(("Funny Cars"));
        funnyCars.getGarageCars().add(audiA3);
        funnyCars.getGarageCars().add(citroenC3);
        funnyCars.getGarageCars().add(fordFiesta);
        funnyCars.getGarageCars().add(fiatStilo);
        funnyCars.getGarageCars().add(hondaCivic);

        Race ourensePrix = new StandardRace("Ourense Prix", "Standard", 120);
        ourensePrix.getParticipatingGarages().add(crazyCars);

        Race vigoPrix = new QualifierRace("Vigo Prix", "Qualifier", 30);
        vigoPrix.getParticipatingGarages().add(crazyCars);
        vigoPrix.getParticipatingGarages().add(funnyCars);

        Race lugoPrix = new StandardRace("Lugo Prix", "Standard");
        lugoPrix.getParticipatingGarages().add(funnyCars);
        lugoPrix.getParticipatingGarages().add(fabulousCars);

        Race santiagoPrix = new QualifierRace("Santiago Prix", "Qualifier", 25);
        santiagoPrix.getParticipatingGarages().add(fabulousCars);

        Tournament galicianTournament = new Tournament("Galician Tournament");
        galicianTournament.getRaces().add(ourensePrix);
        galicianTournament.getRaces().add(vigoPrix);
        galicianTournament.getRaces().add(santiagoPrix);
        galicianTournament.getRaces().add(lugoPrix);
    }
}
