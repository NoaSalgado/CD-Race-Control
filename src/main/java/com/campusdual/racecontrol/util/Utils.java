package com.campusdual.racecontrol.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    private static Random rand = new Random();
    public static int generateRandomNumber(int min, int max){
        max++;
        return rand.nextInt((max -min) + min);
    }

    public static <T> void printFromList(List<T> list){
        for(int i = 0; i < list.size(); i++){
            System.out.println(i + 1 + " - " + list.get(i));
        }
    }

    public static <T> String returnPrintFromList(List<T> list){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            sb.append(list.get(i).toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    public static <T> T printAndSelectFromList(List<T> list){
        printFromList(list);
        int selection = Input.integer();

        while(!isSelectionCorrect(selection, list.size())){
            selection = Input.integer("Opción incorrecta, selecciona una opción válida: ");
        }
        return list.get(selection -1);
    }

    public static boolean isSelectionCorrect(int selection, int listSize){
        return selection >= 1 && selection <= listSize;
    }
}
