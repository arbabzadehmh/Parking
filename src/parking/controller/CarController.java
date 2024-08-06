package parking.controller;

import parking.model.bl.CarBl;
import parking.model.bl.Logger;
import parking.model.entity.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CarController {

    public static Map<String, String> save(String number, boolean vip) {
        Map<String, String> result = new HashMap<>();
        try {
            if (Validator.checkCarNumber(number, 20)) {
                Car car =
                        Car
                                .builder()
                                .carNumber(number)
                                .vip(vip)
                                .deleted(false)
                                .build();
                CarBl.save(car);
                Logger.info("CAR-SAVE", car.toString(), BaseController.user.getId());
                result.put("status", "true");
                result.put("message", car.toString() + " Saved");
            } else {
                Logger.error("CAR-SAVE-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("status", "false");
                result.put("message", "Invalid Data !");
            }
        } catch (Exception e) {
            Logger.error("CAR-SAVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status", "false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> edit(Long id, String number, boolean vip) {
        Map<String, String> result = new HashMap<>();
        try {
            if (Validator.checkCarNumber(number, 20)) {
                Car car =
                        Car
                                .builder()
                                .id(id)
                                .carNumber(number)
                                .vip(vip)
                                .build();
                CarBl.edit(car);
                Logger.info("CAR-EDIT", car.toString(), BaseController.user.getId());
                result.put("status", "true");
                result.put("message", car.toString() + " Edited");
            } else {
                Logger.error("CAR-EDIT-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("status", "false");
                result.put("message", "Invalid Data !");
            }
        } catch (Exception e) {
            Logger.error("CAR-EDIT-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status", "false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> remove(Long id) {
        Map<String, String> result = new HashMap<>();
        try {
            Car car = CarBl.findById(id);
            CarBl.remove(id);
            Logger.info("CAR-REMOVE", car.toString(), BaseController.user.getId());
            result.put("status", "true");
            result.put("message", car.toString() + " Removed");
        } catch (Exception e) {
            Logger.error("CAR-REMOVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status", "false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, List<Car>> findAll() {
        Map<String, List<Car>> result = new HashMap<>();
        try {
            List<Car> carList = CarBl.findAll();
            Logger.info("CAR-FIND", "ALL", BaseController.user.getId());
            result.put("ok", carList);
        } catch (Exception e) {
            Logger.error("CAR-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<Car>> findById(Long id) {
        Map<String, List<Car>> result = new HashMap<>();
        try {
            Car car = CarBl.findById(id);
            List<Car> carList = new ArrayList<>();
            carList.add(car);
            Logger.info("CAR-FIND-BY ID", car.toString(), BaseController.user.getId());
            result.put("ok", carList);
        } catch (Exception e) {
            Logger.error("CAR-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<Car>> findByNumber(String carNumber) {
        Map<String, List<Car>> result = new HashMap<>();
        try {
            if (Validator.checkCarNumber(carNumber, 20)) {
                Car car = CarBl.findByNumber(carNumber);
                List<Car> carList = new ArrayList<>();
                carList.add(car);
                Logger.info("CAR-FIND", car.toString(), BaseController.user.getId());
                result.put("ok", carList);
            } else {
                Logger.error("CAR-FIND-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("Invalid Data !", null);
            }
        } catch (Exception e) {
            Logger.error("CAR-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Car findByNumberTextField(String carNumber) {
        Car car = null;
        try {
            car = CarBl.findByNumber(carNumber);
        } catch (Exception e) {

        }
        return car;
    }

    public static Map<String, List<Car>> findByVipStatus(boolean status) {
        Map<String, List<Car>> result = new HashMap<>();
        try {
            List<Car> carList = CarBl.findByVipStatus(status);
            Logger.info("CAR-FIND", "BY CAR STATUS", BaseController.user.getId());
            result.put("ok", carList);
        } catch (Exception e) {
            Logger.error("CAR-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static Map<String, List<Car>> findOrSaveByNumber(String carNumber) {
        Map<String, List<Car>> result = new HashMap<>();
        try {
            if (Validator.checkCarNumber(carNumber, 20)) {
                Car car = CarBl.findOrSaveByNumber(carNumber);
                List<Car> carList = new ArrayList<>();
                carList.add(car);
                Logger.info("CAR-FIND-SAVE", car.toString(), BaseController.user.getId());
                result.put("ok", carList);
            } else {
                Logger.error("CAR-FIND-ERROR", "INVALID DATA !", BaseController.user.getId());
                result.put("Invalid Data !", null);
            }
        } catch (Exception e) {
            Logger.error("CAR-FIND-SAVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }


}
