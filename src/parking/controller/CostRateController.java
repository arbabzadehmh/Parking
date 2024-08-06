package parking.controller;


import parking.model.bl.CostRateBl;
import parking.model.bl.Logger;

import parking.model.entity.CostRate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostRateController {

    public static Map<String, String> save(double hourlyRate, double dailyRate, double holidaysHourlyRate, double entranceFee) {
        Map<String, String> result = new HashMap<>();
        try {
            CostRate costRate =
                    CostRate
                            .builder()
                            .id(1L)
                            .hourlyRate(hourlyRate)
                            .dailyRate(dailyRate)
                            .holidaysHourlyRate(holidaysHourlyRate)
                            .entranceFee(entranceFee)
                            .build();

            CostRateBl.save(costRate);

            Logger.info("COST-RATE-SAVE", String.valueOf(costRate.getDailyRate()), BaseController.user.getId());
            result.put("status", "true");
            result.put("message", costRate.toString() + " Saved");
        }catch (Exception e){
            Logger.error("COST-RATE-SAVE-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status", "false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, String> edit(double hourlyRate, double dailyRate, double holidaysHourlyRate, double entranceFee) {
        Map<String, String> result = new HashMap<>();
        try {
            CostRate costRate =
                    CostRate
                            .builder()
                            .hourlyRate(hourlyRate)
                            .dailyRate(dailyRate)
                            .holidaysHourlyRate(holidaysHourlyRate)
                            .entranceFee(entranceFee)
                            .build();

            CostRateBl.edit(costRate);

            Logger.info("COST-RATE-EDIT", String.valueOf(costRate.getDailyRate()), BaseController.user.getId());
            result.put("status", "true");
            result.put("message", costRate.toString() + " Edited");
        }catch (Exception e){
            Logger.error("COST-RATE-EDIT-ERROR", e.getMessage(), BaseController.user.getId());
            result.put("status", "false");
            result.put("message", e.getMessage());
        }
        return result;
    }

    public static Map<String, List<CostRate>> findAll() {
        Map<String, List<CostRate>> result = new HashMap<>();
        try {
            List<CostRate> costRates = CostRateBl.findAll();
            Logger.info("COST-RATE-FIND", "ALL", BaseController.user.getId());
            result.put("ok", costRates);
        } catch (Exception e) {
            Logger.error("COST-RATE-FIND-ERROR", e.getMessage(), BaseController.user.getId());
            result.put(e.getMessage(), null);
        }
        return result;
    }

    public static CostRate find() {
        CostRate costRate = null;
        try {
            costRate = CostRateBl.find();
            Logger.info("COST-RATE-FIND" , String.valueOf(costRate.getDailyRate()), BaseController.user.getId());
        }catch (Exception e){
            Logger.error("COST-RATE-FIND-ERROR", e.getMessage(), BaseController.user.getId());
        }
        return costRate;
    }

}
