package parking.model;

import com.google.gson.Gson;
import parking.controller.*;
import parking.model.bl.*;
import parking.model.da.CostRateDa;
import parking.model.da.ParkingEntranceDa;
import parking.model.entity.Car;
import parking.model.entity.CostRate;
import parking.model.utils.Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {



//        Car car = Car.builder().id(5L).carNumber("70 Q 548 10").vip(false).build();
//        Car car1 = Car.builder().id(6L).carNumber("80 T 555 10").vip(true).build();
//        CarDa carDa = new CarDa();
//        System.out.println(carDa.remove(5L));
//        System.out.println(carDa.findByNumber("36 T 325 79"));

//        System.out.println(CarBl.findByNumber("21 A 654 "));

//        ParkinSpot parkinSpot = ParkinSpot.builder().id(9L).name("G9").status(true).vipParking(true).build();
//        ParkingSpotDa parkingSpotDa = new ParkingSpotDa();
//        System.out.println(parkingSpotDa.remove(16L));

//        System.out.println(ParkingSpotBl.findAll());

//        ParkingBill parkingBill =
//                ParkingBill
//                        .builder()
//                        .id(3L)
//                        .car(carDa.findByID(4L))
//                        .parkinSpot(parkingSpotDa.findById(15L))
//                        .entranceTime(LocalDateTime.now())
//                        .cost(20)
//                        .build();
//
//        ParkingBillDa parkingBillDa = new ParkingBillDa();
//        System.out.println(parkingBillDa.findByCarNumber("36 T 325 79"));
//        System.out.println(parkingBillDa.isVipCar(2L));

//        ParkingBillBl.costCalculator(2L);

//        ParkingEntranceController.save("36 T 325 79",LocalDateTime.now(),"V3");

//        ParkingBillController.save(1L,LocalDateTime.of(2023,11,2,12,0));

//        CarController.findByVipStatus(true);

//        Gson gson = new Gson();
//        List<String> emptySpotNames = new ArrayList<>();
//        emptySpotNames = gson.fromJson(ParkingSpotController.findEmptySpot(), ArrayList.class);
//        for (String spot : emptySpotNames){
//            System.out.println(spot);
//        }
//
//        List<String> spotNames = new ArrayList<>();
//        spotNames = gson.fromJson(ParkingSpotController.findAllNames(), ArrayList.class);
//        for (String spot : spotNames){
//            System.out.println(spot);
//        }

//        UserController.findAll();

//        System.out.println(UserController.login("hasa123", "hasan456"));

//        UserController.login("hasan123", "hasan456");
//        System.out.println(ParkingEntranceController.findByEntranceTime(LocalDateTime.of(2023,12,26,18,27)));
//        System.out.println(ParkingEntranceBl.findByEntranceTime(LocalDateTime.of(2023,12,26,18,27)));
//        ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa();
//        System.out.println(parkingEntranceDa.findByEntranceTime(LocalDateTime.of(2023,12,26,18,35)));
//        LocalDateTime s = LocalDateTime.now().withSecond(0).withNano(0);
//        System.out.println(s);

        CostRateDa costRateDa = new CostRateDa();
        CostRate costRate = CostRate.builder().hourlyRate(77).dailyRate(777).holidaysHourlyRate(77).entranceFee(7777).build();
//        costRateDa.edit(costRate);
//        CostRateBl.edit(costRate);
        System.out.println(CostRateController.edit(88,88,88,88));


    }
}
