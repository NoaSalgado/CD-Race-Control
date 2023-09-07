package com.campusdual.racecontrol.util;

import java.util.Random;

public class Utils {
    public static int generateRandomNumber(int maxNumber){
        Random rand = new Random();
        return rand.nextInt(maxNumber);
    }
}
