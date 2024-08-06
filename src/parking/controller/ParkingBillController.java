package parking.controller;

import parking.model.bl.*;
import parking.model.entity.ParkingBill;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ParkingBillController {

    public static Map<String, String> save(Long entranceId) {
        Map<String, String> result = new HashMap<>();
        try {
            ParkingBill parkingBill =
                    ParkingBill
                            .builder()
                            .parkingEntrance(ParkingEntranceBl.findById(entranceId))
                            .exitTime(LocalDateTime.now().withSecond(0).withNano(0))
                            .deleted(false)
                            .build();

            ParkingBillBl.save(parkingBill);
            ParkingBillBl.costCalculator(parkingBill.getId());
            ParkingSpotBl.unloadParkingSpot(parkingBill.getParkingEntrance().getParkingSpot().getName());

            parkingBill = ParkingBillBl.findById(parkingBill.getId());
            Logger.info("BILL-SAVE", "ID : " + String.valueOf(parkingBill.getId()), BaseController.user.getId());
            result.put("status","true");
            result.put("message", parkingBill.toString() +" Saved");
        } catch (Exception e) {
            Logger.error("BILL-SAVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> edit(Long id, Long entranceId, LocalDateTime exitTime){
        Map<String, String> result = new HashMap<>();
        try {
            ParkingBill parkingBill =
                    ParkingBill
                            .builder()
                            .id(id)
                            .parkingEntrance(ParkingEntranceBl.findById(entranceId))
                            .exitTime(exitTime)
                            .cost(0)
                            .build();

            ParkingBillBl.edit(parkingBill);
            ParkingBillBl.costCalculator(parkingBill.getId());
            Logger.info("BILL-EDIT","ID : " + String.valueOf(parkingBill.getId()), BaseController.user.getId());
            result.put("status","true");
            result.put("message", parkingBill.toString() +" Edited");
        }catch (Exception e){
            Logger.error("BILL-EDIT-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> remove(Long id){
        Map<String, String> result = new HashMap<>();
        try {
            ParkingBill parkingBill = ParkingBillBl.findById(id);
            ParkingBillBl.remove(id);
            ParkingSpotBl.spotPicker(parkingBill.getParkingEntrance().getParkingSpot().getName());
            Logger.info("BILL-REMOVE", "ID : " + String.valueOf(parkingBill.getId()), BaseController.user.getId());
            result.put("status","true");
            result.put("message", parkingBill.toString() +" Removed");
        }catch (Exception e){
            Logger.error("BILL-REMOVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status","false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, List<ParkingBill>> findAll(){
        Map<String, List<ParkingBill>> result = new HashMap<>();
        try {
            List<ParkingBill> parkingBillList = ParkingBillBl.findAll();
            Logger.info("BILL-FIND", "ALL", BaseController.user.getId());
            result.put("ok", parkingBillList);
        }catch (Exception e){
            Logger.error("BILL-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingBill>> findById(Long id){
        Map<String, List<ParkingBill>> result = new HashMap<>();
        try {
            ParkingBill parkingBill = ParkingBillBl.findById(id);
            List<ParkingBill> parkingBillList = new ArrayList<>();
            parkingBillList.add(parkingBill);
            Logger.info("BILL-FIND- BY ID", String.valueOf(parkingBill.getId()), BaseController.user.getId());
            result.put("ok", parkingBillList);
        }catch (Exception e){
            Logger.error("BILL-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingBill>> findByCarNumber(String carNumber){
        Map<String, List<ParkingBill>> result = new HashMap<>();
        try {
            if (Validator.checkCarNumber(carNumber,20)){
                List<ParkingBill> parkingBillList = ParkingBillBl.findByCarNumber(carNumber);
                Logger.info("BILL-FIND","BY CAR NUMBER", BaseController.user.getId());
                result.put("ok", parkingBillList);
            }else {
                Logger.error("BILL-FIND-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("Invalid Data !", null);
            }
        }catch (Exception e){
            Logger.error("BILL-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static String costCalculator(Long id){
        String message;
        try {
            ParkingBillBl.costCalculator(id);
            message = ParkingBillBl.findById(id).toString();
            Logger.info("COST-CALCULATE", "ID : " + String.valueOf(id), BaseController.user.getId());
        }catch (Exception e){
            message = "Error : " + e.getMessage();
            Logger.error("COST-CALCULATE-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return message;
    }

    public static Map<String, List<ParkingBill>> findByExitTime(LocalDateTime exitTime){
        Map<String, List<ParkingBill>> result = new HashMap<>();
        try {
            List<ParkingBill> parkingBillList = ParkingBillBl.findByExitTime(exitTime);
            Logger.info("BILL-FIND", "BY EXIT TIME",BaseController.user.getId());
            result.put("ok", parkingBillList);
        }catch (Exception e){
            Logger.error("BILL-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<ParkingBill>> findByEntranceId(Long entId){
        Map<String, List<ParkingBill>> result = new HashMap<>();
        try {
            ParkingBill parkingBill = ParkingBillBl.findByEntranceId(entId);
            List<ParkingBill> parkingBillList = new ArrayList<>();
            parkingBillList.add(parkingBill);
            Logger.info("BILL-FIND" ,"ID : " + String.valueOf(parkingBill.getId()),BaseController.user.getId());
            result.put("ok", parkingBillList);
        }catch (Exception e){
            Logger.error("BILL-FIND-ERROR",e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static ParkingBill findByEntranceIdTextField(Long entId){
        ParkingBill parkingBill = null;
        try {
            parkingBill = ParkingBillBl.findByEntranceId(entId);
            Logger.info("BILL-FIND" ,"ID : " + String.valueOf(parkingBill.getId()),BaseController.user.getId());

        }catch (Exception e){
            Logger.error("BILL-FIND-ERROR",e.getMessage(), BaseController.user.getId());
        }
        return parkingBill;
    }

}
