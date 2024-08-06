package parking.controller;


import parking.model.bl.CarBl;
import parking.model.bl.Logger;
import parking.model.bl.ParkingEntranceBl;
import parking.model.bl.ParkingSpotBl;
import parking.model.entity.Car;
import parking.model.entity.ParkingEntrance;
import parking.model.entity.ParkingSpot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ParkingEntranceController {

    public static Map<String, String> save(String carNumber, String spotName) {
        Map<String, String> result = new HashMap<>();
        try {
            if (Validator.checkCarNumber(carNumber, 20) && Validator.checkParkingSpotName(spotName)){
                ParkingSpotBl.findEmptySpot();
                Car car = CarBl.findOrSaveByNumber(carNumber);
                String spot;
                if (car.isVip()) {
                    if (ParkingSpotBl.remainingCapacityOfVipParking() > 0) {
                        spot = ParkingSpotBl.vipParkingSelector(spotName);
                    } else {
                        spot = ParkingSpotBl.generalParkingSelector(spotName);
                    }
                } else {
                    ParkingSpotBl.findEmptyGeneralSpot();
                    spot = ParkingSpotBl.generalParkingSelector(spotName);
                }

                ParkingSpot parkingSpot = ParkingSpotBl.findByName(spot);

                ParkingEntrance parkingEntrance =
                        ParkingEntrance
                                .builder()
                                .car(CarBl.findByNumber(carNumber))
                                .entranceTime(LocalDateTime.now().withSecond(0).withNano(0))
                                .parkingSpot(parkingSpot)
                                .deleted(false)
                                .build();

                ParkingEntranceBl.save(parkingEntrance);
                Logger.info("ENTRANCE-SAVE", "ID : " + String.valueOf(parkingEntrance.getId()), BaseController.user.getId());
                result.put("status","true");
                result.put("message", parkingEntrance.toString() +" Saved");
            }else {
                Logger.error("ENTRANCE-EDIT-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("status","false");
                result.put("message", "Invalid Data !");
            }
        } catch (Exception e) {
            Logger.error("ENTRANCE-SAVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> edit(long id, String carNumber, LocalDateTime entranceTime, String spotName) {
        Map<String, String> result = new HashMap<>();
        try {
            Car car = CarBl.findOrSaveByNumber(carNumber);

            if (Validator.checkCarNumber(carNumber,20) && Validator.checkParkingSpotName(spotName)) {

                ParkingSpotBl.unloadParkingSpot(ParkingEntranceBl.findById(id).getParkingSpot().getName());

                ParkingEntrance parkingEntrance =
                        ParkingEntrance
                                .builder()
                                .id(id)
                                .car(CarBl.findByNumber(carNumber))
                                .entranceTime(entranceTime)
                                .parkingSpot(ParkingSpotBl.findByName(spotName))
                                .build();
                ParkingEntranceBl.edit(parkingEntrance);
                ParkingSpotBl.spotPicker(parkingEntrance.getParkingSpot().getName());
                Logger.info("ENTRANCE-EDITED", "ID : " + String.valueOf(parkingEntrance.getId()), BaseController.user.getId());
                result.put("status","true");
                result.put("message", parkingEntrance.toString() +" Edited");
            } else {
                Logger.error("ENTRANCE-EDIT-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("status","false");
                result.put("message", "Invalid Data !");
            }
        } catch (Exception e) {
            Logger.error("ENTRANCE-EDIT-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> remove(Long id) {
        Map<String, String> result = new HashMap<>();
        try {
            ParkingEntrance parkingEntrance = ParkingEntranceBl.findById(id);
            ParkingEntranceBl.remove(id);
            ParkingSpotBl.unloadParkingSpot(parkingEntrance.getParkingSpot().getName());
            Logger.info("ENTRANCE-REMOVED", "ID : " + String.valueOf(parkingEntrance.getId()), BaseController.user.getId());
            result.put("status","true");
            result.put("message", parkingEntrance.toString() +" Removed");
        } catch (Exception e) {
            Logger.error("ENTRANCE-REMOVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, List<ParkingEntrance>> findAll() {
        Map<String, List<ParkingEntrance>> result = new HashMap<>();
        try {
            List<ParkingEntrance> parkingEntranceList = ParkingEntranceBl.findAll();
            Logger.info("ENTRANCE-FIND", "ALL", BaseController.user.getId());
            result.put("ok", parkingEntranceList);
        } catch (Exception e) {
            Logger.error("ENTRANCE-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingEntrance>> findById(Long id) {
        Map<String, List<ParkingEntrance>> result = new HashMap<>();
        try {
            ParkingEntrance parkingEntrance = ParkingEntranceBl.findById(id);
            List<ParkingEntrance> parkingEntranceList = new ArrayList<>();
            parkingEntranceList.add(parkingEntrance);
            Logger.info("ENTRANCE-FIND-BY ID", String.valueOf(parkingEntrance.getId()), BaseController.user.getId());
            result.put("ok", parkingEntranceList);
        } catch (Exception e) {
            Logger.error("ENTRANCE-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingEntrance>> findByCarNumber(String carNumber) {
        Map<String, List<ParkingEntrance>> result = new HashMap<>();
        try {
            if (Validator.checkCarNumber(carNumber, 20)) {
                List<ParkingEntrance> parkingEntranceList = ParkingEntranceBl.findByCarNumber(carNumber);
                Logger.info("ENTRANCE-FIND", "BY CAR NUMBER", BaseController.user.getId());
                result.put("ok", parkingEntranceList);
            } else {
                Logger.error("ENTRANCE-FIND-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("Invalid Data !", null);
            }
        } catch (Exception e) {
            Logger.error("ENTRANCE-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingEntrance>> findByEntranceTime(LocalDateTime entranceTime){
        Map<String, List<ParkingEntrance>> result = new HashMap<>();
        try {
            List<ParkingEntrance> parkingEntranceList = ParkingEntranceBl.findByEntranceTime(entranceTime);
            Logger.info("ENTRANCE-FIND", "BY ENTRANCE TIME", BaseController.user.getId());
            result.put("ok", parkingEntranceList);
        }catch (Exception e){
            Logger.error("ENTRANCE-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingEntrance>> findBySpotName(String spotName){
        Map<String, List<ParkingEntrance>> result = new HashMap<>();
        try {
            if (Validator.checkParkingSpotName(spotName)){
                List<ParkingEntrance> parkingEntranceList = ParkingEntranceBl.findBySpotName(spotName);
                Logger.info("ENTRANCE-FIND", "BY SPOT NAME", BaseController.user.getId());
                result.put("ok", parkingEntranceList);
            }else {
                Logger.error("ENTRANCE-FIND-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("Invalid Data !", null);
            }
        }catch (Exception e){
            Logger.error("ENTRANCE-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

}
