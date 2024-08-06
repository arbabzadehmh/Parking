package parking.controller;

import java.util.regex.Pattern;

public class Validator {

    public static boolean checkCarNumber(String number, int length){
        return Pattern.matches("[A-Z\\d\\s]{5," + length + "}", number);
    }

    public static boolean checkName(String name, int length){
        return Pattern.matches("[A-Za-z\\s]{2," + length + "}", name);
    }

    public static boolean checkUserNameAndPassword(String text, int length){
        return Pattern.matches("[A-Za-z\\d\\s]{3," + length + "}", text);
    }

    public static boolean checkParkingSpotName(String text){
        return Pattern.matches("[A-Z\\d]{2,10}", text);
    }

}
