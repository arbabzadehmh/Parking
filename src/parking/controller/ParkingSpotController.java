package parking.controller;

import parking.model.bl.Logger;
import parking.model.bl.ParkingSpotBl;
import parking.model.entity.ParkingSpot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ParkingSpotController {

    public static Map<String, String> save(String name, boolean vipParking){
        Map<String, String> result = new HashMap<>();
        try {
            if (Validator.checkParkingSpotName(name)){
                ParkingSpot parkingSpot =
                        ParkingSpot
                                .builder()
                                .name(name)
                                .status(true)
                                .vipParking(vipParking)
                                .deleted(false)
                                .build();

                ParkingSpotBl.save(parkingSpot);
                Logger.info("PARKING SPOT-SAVE", parkingSpot.toString(),BaseController.user.getId());
                result.put("status","true");
                result.put("message", parkingSpot.toString() +" Saved");
            }else {
                Logger.error("PARKING SPOT-SAVE-ERROR","INVALID DATA !", BaseController.user.getId());
                result.put("status","false");
                result.put("message", "Invalid Data !");
            }
        }catch (Exception e){
            Logger.error("PARKING SPOT-SAVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> edit(Long id, String name, boolean status, boolean vipParking){
        Map<String, String> result = new HashMap<>();
        try {
            if (Validator.checkParkingSpotName(name)){
                ParkingSpot parkingSpot =
                        ParkingSpot
                                .builder()
                                .id(id)
                                .name(name)
                                .status(status)
                                .vipParking(vipParking)
                                .build();

                ParkingSpotBl.edit(parkingSpot);
                Logger.info("PARKING SPOT-EDIT", parkingSpot.toString(),BaseController.user.getId());
                result.put("status","true");
                result.put("message", parkingSpot.toString() +" Edited");
            } else{
                Logger.error("PARKING SPOT-EDIT-ERROR","INVALID DATA !", BaseController.user.getId());
                result.put("status","false");
                result.put("message", "Invalid Data !");
            }
        }catch (Exception e){
            Logger.error("PARKING SPOT-EDIT-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> remove(Long id){
        Map<String, String> result = new HashMap<>();
        try {
            ParkingSpot parkingSpot = ParkingSpotBl.findById(id);
            ParkingSpotBl.remove(id);
            Logger.info("PARKING SPOT-REMOVE", parkingSpot.toString(),BaseController.user.getId());
            result.put("status","true");
            result.put("message", parkingSpot.toString() +" Removed");
        }catch (Exception e){
            Logger.error("PARKING SPOT-REMOVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, List<ParkingSpot>> findAll(){
        Map<String, List<ParkingSpot>> result = new HashMap<>();
        try {
            List<ParkingSpot> parkingSpotList = ParkingSpotBl.findAll();
            Logger.info("PARKING SPOT-FIND","ALL", BaseController.user.getId());
            result.put("ok", parkingSpotList);
        }catch (Exception e){
            Logger.error("PARKING SPOT-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingSpot>> findById(Long id){
        Map<String, List<ParkingSpot>> result = new HashMap<>();
        try {
            ParkingSpot parkingSpot = ParkingSpotBl.findById(id);
            List<ParkingSpot> parkingSpotList = new ArrayList<>();
            parkingSpotList.add(parkingSpot);
            Logger.info("PARKING SPOT-FIND",parkingSpot.toString(), BaseController.user.getId());
            result.put("ok", parkingSpotList);
        }catch (Exception e){
            Logger.error("PARKING SPOT-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingSpot>> findByName(String name){
        Map<String, List<ParkingSpot>> result = new HashMap<>();
        try {
            if (Validator.checkParkingSpotName(name)){
                ParkingSpot parkingSpot = ParkingSpotBl.findByName(name);
                List<ParkingSpot> parkingSpotList = new ArrayList<>();
                parkingSpotList.add(parkingSpot);
                Logger.info("PARKING SPOT-FIND",parkingSpot.toString(), BaseController.user.getId());
                result.put("ok", parkingSpotList);
            }else {
                Logger.error("FIND-ERROR","INVALID DATA !", BaseController.user.getId());
                result.put("Invalid Data !", null);
            }
        }catch (Exception e){
            Logger.error("PARKING SPOT-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingSpot>> findByStatus(boolean status){
        Map<String, List<ParkingSpot>> result = new HashMap<>();
        try {
            List<ParkingSpot> parkingSpotList = ParkingSpotBl.findByStatus(status);
            Logger.info("PARKING SPOT-FIND","BY STATUS", BaseController.user.getId());
            result.put("ok", parkingSpotList);
        }catch (Exception e){
            Logger.error("PARKING SPOT-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingSpot>> findByVipStatus(boolean status){
        Map<String, List<ParkingSpot>> result = new HashMap<>();
        try {
            List<ParkingSpot> parkingSpotList = ParkingSpotBl.findByVipStatus(status);
            Logger.info("PARKING SPOT-FIND","BY VIP STATUS", BaseController.user.getId());
            result.put("ok", parkingSpotList);
        }catch (Exception e){
            Logger.error("PARKING SPOT-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static int remainingCapacityOfGeneralParking(){
        int capacity = 0;
        try {
            capacity = ParkingSpotBl.remainingCapacityOfGeneralParking();
            Logger.info("GENERAL PARKING CAPACITY", String.valueOf(capacity), BaseController.user.getId());
        }catch (Exception e){
            Logger.error("GENERAL PARKING CAPACITY-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return capacity;
    }

    public static int remainingCapacityOfVipParking(){
        int capacity = 0;
        try {
            capacity = ParkingSpotBl.remainingCapacityOfVipParking();
            Logger.info("VIP PARKING CAPACITY", String.valueOf(capacity), BaseController.user.getId());
        }catch (Exception e){
            Logger.error("VIP PARKING CAPACITY-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return capacity;
    }

    public String findEmptyGeneralSpot(){
        String message;
        try {
            message = ParkingSpotBl.findEmptyGeneralSpot().toString();
            Logger.info("EMPTY GENERAL SPOT",message, BaseController.user.getId());
        }catch (Exception e){
            message = "Error : " + e.getMessage();
            Logger.error("EMPTY GENERAL SPOT-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return message;
    }

    public String findEmptyVipSpot(){
        String message;
        try {
            message = ParkingSpotBl.findEmptyVipSpot().toString();
            Logger.info("EMPTY VIP SPOT",message, BaseController.user.getId());
        }catch (Exception e){
            message = "Error : " + e.getMessage();
            Logger.error("EMPTY VIP SPOT-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return message;
    }

    public static List<String> findEmptySpot(){
        List<String> emptySpots = new ArrayList<>();
        try {
            emptySpots = ParkingSpotBl.findEmptySpot();
            Logger.info("EMPTY SPOT","FIND", BaseController.user.getId());
        }catch (Exception e){
            Logger.error("EMPTY SPOT-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return emptySpots;
    }

    public static String generalParkingSelector(String spotName){
        String message;
        try {
            message = ParkingSpotBl.generalParkingSelector(spotName);
            Logger.info("GENERAL PARKING SELECT",message, BaseController.user.getId());
        }catch (Exception e){
            message = "Error : " + e.getMessage();
            Logger.error("GENERAL PARKING SELECT-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return message;
    }

    public static String vipParkingSelector(String spotName){
        String message;
        try {
            message = ParkingSpotBl.vipParkingSelector(spotName);
            Logger.info("VIP PARKING SELECT",message, BaseController.user.getId());
        }catch (Exception e){
            message = "Error : " + e.getMessage();
            Logger.error("VIP PARKING SELECT-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return message;
    }

    public static String unloadParkingSpot(String spotName){
        String message;
        try {
            if (Validator.checkParkingSpotName(spotName)) {
                message = ParkingSpotBl.unloadParkingSpot(spotName);
                Logger.info("UNLOAD SPOT", message, BaseController.user.getId());
            }else {
                message = "Invalid Data !";
                Logger.error("UNLOAD SPOT-ERROR",message, BaseController.user.getId());
            }
        }catch (Exception e){
            message = "Error : " + e.getMessage();
            Logger.error("UNLOAD SPOT-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return message;
    }

    public static String spotPicker(String spotName){
        String message;
        try {
            if (Validator.checkParkingSpotName(spotName)) {
                message = ParkingSpotBl.spotPicker(spotName);
                Logger.info("PICK SPOT", message, BaseController.user.getId());
            }else {
                message = "Invalid Data !";
                Logger.error("PICK-SPOT-ERROR", message, BaseController.user.getId());
            }
        }catch (Exception e){
            message = "Error : " + e.getMessage();
            Logger.error("PICK-SPOT-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return message;
    }

    public static String findAllNames(){
        String message;
        try {
            message = ParkingSpotBl.findAllNames().toString();
            Logger.info("FIND-SPOT-NAMES", message, BaseController.user.getId());
        }catch (Exception e){
            message = "Error : " + e.getMessage();
            Logger.error("FIND-SPOT-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return message;
    }

}
