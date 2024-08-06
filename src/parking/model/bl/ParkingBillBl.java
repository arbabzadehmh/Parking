package parking.model.bl;

import parking.controller.BaseController;
import parking.controller.exception.NoContentException;
import parking.model.da.ParkingBillDa;
import parking.model.entity.ParkingBill;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class ParkingBillBl {

    public static ParkingBill save(ParkingBill parkingBill) throws Exception {
        try (ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            return parkingBillDa.save(parkingBill);
        }
    }

    public static ParkingBill edit(ParkingBill parkingBill) throws Exception {
        try (ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            if (parkingBillDa.findById(parkingBill.getId()) != null) {
                return parkingBillDa.edit(parkingBill);
            }
            throw new NoContentException("Bill is not in the list !");
        }
    }

    public static ParkingBill remove(Long id) throws Exception {
        try (ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            ParkingBill parkingBill = parkingBillDa.findById(id);
            if (parkingBill != null) {
                parkingBillDa.remove(id);
                return parkingBill;
            }
            throw new NoContentException("Bill is not in the list !");
        }
    }

    public static List<ParkingBill> findAll() throws Exception {
        try (ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            List<ParkingBill> parkingBillList = parkingBillDa.findAll();
            if (parkingBillList.size() > 0) {
                return parkingBillList;
            }
            throw new NoContentException("The list is empty !");
        }
    }

    public static ParkingBill findById(Long id) throws Exception {
        try (ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            ParkingBill parkingBill = parkingBillDa.findById(id);
            if (parkingBill != null) {
                return parkingBill;
            }
            throw new NoContentException("No Bill was found !");
        }
    }

    public static List<ParkingBill> findByCarNumber(String carNumber) throws Exception {
        try (ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            List<ParkingBill> parkingBillList = parkingBillDa.findByCarNumber(carNumber);
            if (parkingBillList.size() > 0) {
                return parkingBillList;
            }
            throw new NoContentException("No Bill was found !");
        }
    }

    public static void costCalculator(Long id) throws Exception {
        try (ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            ParkingBill parkingBill = parkingBillDa.findById(id);
            double cost;
            if (parkingBill != null) {
                if (parkingBill.getExitTime() == null) {
                    cost = 0;
                } else if (parkingBill.getParkingEntrance().getEntranceTime().getDayOfYear() == parkingBill.getExitTime().getDayOfYear()) {
                    if (parkingBill.getExitTime().getDayOfWeek() == DayOfWeek.THURSDAY || parkingBill.getExitTime().getDayOfWeek() == DayOfWeek.FRIDAY) {
                        cost =BaseController.costRate.getEntranceFee() + BaseController.costRate.getHolidaysHourlyRate() * (parkingBill.getExitTime().getHour() - parkingBill.getParkingEntrance().getEntranceTime().getHour());
                    } else {
                        cost = BaseController.costRate.getEntranceFee() + BaseController.costRate.getHourlyRate() * (parkingBill.getExitTime().getHour() - parkingBill.getParkingEntrance().getEntranceTime().getHour());
                    }
                } else {
                    cost = BaseController.costRate.getEntranceFee() + BaseController.costRate.getDailyRate() * (parkingBill.getExitTime().getDayOfYear() - parkingBill.getParkingEntrance().getEntranceTime().getDayOfYear());
                }
                if (parkingBill.getParkingEntrance().getCar().isVip()) {
                    cost = 0;
                }
                parkingBill.setCost(cost);
                parkingBillDa.costCalculator(parkingBill);
            } else {
                throw new NoContentException("No bill was found !");
            }
        }
    }

    public static List<ParkingBill> findByExitTime(LocalDateTime exitTime) throws Exception{
        try(ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            List<ParkingBill> parkingBillList = parkingBillDa.findByExitTime(exitTime);
            if (parkingBillList.size()>0){
                return parkingBillList;
            }
            throw new NoContentException("No bill was found !");
        }
    }

    public static ParkingBill findByEntranceId(Long entId) throws Exception {
        try(ParkingBillDa parkingBillDa = new ParkingBillDa()) {
            ParkingBill parkingBill = parkingBillDa.findByEntranceId(entId);
            if (parkingBill != null){
                return parkingBill;
            }
            throw new NoContentException("No bill was found !");
        }
    }

}
