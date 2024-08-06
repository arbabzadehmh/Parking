package parking.model.bl;

import parking.controller.exception.DuplicateException;
import parking.controller.exception.NoContentException;
import parking.model.da.ParkingSpotDa;
import parking.model.entity.ParkingSpot;

import java.util.List;

public class ParkingSpotBl {

    public static ParkingSpot save(ParkingSpot parkingSpot) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            if (parkingSpotDa.findByName(parkingSpot.getName()) == null) {
                return parkingSpotDa.save(parkingSpot);
            }
            throw new DuplicateException("Parking spot is exist !");
        }
    }

    public static ParkingSpot edit(ParkingSpot parkingSpot) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            if (parkingSpotDa.findById(parkingSpot.getId()) != null) {
                return parkingSpotDa.edit(parkingSpot);
            }
            throw new NoContentException("Parking spot is not in the list !");
        }
    }

    public static ParkingSpot remove(Long id) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            ParkingSpot parkingSpot = parkingSpotDa.findById(id);
            if (parkingSpot != null) {
                parkingSpotDa.remove(id);
                return parkingSpot;
            }
            throw new NoContentException("Parking spot is not in the list !");
        }
    }

    public static List<ParkingSpot> findAll() throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            List<ParkingSpot> parkingSpotList = parkingSpotDa.findAll();
            if (parkingSpotList.size() > 0) {
                return parkingSpotList;
            }
            throw new NoContentException("The list is empty !");
        }
    }

    public static ParkingSpot findById(Long id) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            ParkingSpot parkingSpot = parkingSpotDa.findById(id);
            if (parkingSpot != null) {
                return parkingSpot;
            }
            throw new NoContentException("No parking spot was found !");
        }
    }

    public static ParkingSpot findByName(String name) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            ParkingSpot parkingSpot = parkingSpotDa.findByName(name);
            if (parkingSpot != null) {
                return parkingSpot;
            }
            throw new NoContentException("No parking spot was found !");
        }
    }

    public static List<ParkingSpot> findByStatus(boolean status) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            List<ParkingSpot> parkingSpotList = parkingSpotDa.findByStatus(status);
            if (parkingSpotList.size() > 0) {
                return parkingSpotList;
            }
            throw new NoContentException("No parking spot was found !");
        }
    }

    public static List<ParkingSpot> findByVipStatus(boolean status) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            List<ParkingSpot> parkingSpotList = parkingSpotDa.findByVipStatus(status);
            if (parkingSpotList.size() > 0) {
                return parkingSpotList;
            }
            throw new NoContentException("No parking spot was found !");
        }
    }

    public static int remainingCapacityOfGeneralParking() throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            return parkingSpotDa.remainingCapacityOfGeneralParking();
        }
    }

    public static int remainingCapacityOfVipParking() throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            return parkingSpotDa.remainingCapacityOfVipParking();
        }
    }

    public static List<String> findEmptyGeneralSpot() throws Exception{
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            List<String> emptyGeneralSpots = parkingSpotDa.findEmptyGeneralSpot();
            if (emptyGeneralSpots.size()>0){
                return emptyGeneralSpots;
            }
            throw new NoContentException("No empty parking spot was found !");
        }
    }

    public static List<String> findEmptyVipSpot() throws Exception{
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            List<String> emptyVipSpots = parkingSpotDa.findEmptyVipSpot();
            if (emptyVipSpots.size()>0){
                return emptyVipSpots;
            }
            throw new NoContentException("No empty parking spot was found !");
        }
    }

    public static List<String> findEmptySpot() throws Exception{
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            List<String> emptyVipSpots = parkingSpotDa.findEmptySpot();
            if (emptyVipSpots.size()>0){
                return emptyVipSpots;
            }
            throw new NoContentException("Parking is full !");
        }
    }

    public static String generalParkingSelector(String spotName) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            ParkingSpot parkingSpot = parkingSpotDa.findByName(spotName);
            if (parkingSpot != null && parkingSpot.isStatus() && !(parkingSpot.isVipParking())){
                parkingSpotDa.generalParkingSelector(spotName);
                return spotName;
            }
            throw new NoContentException("The selected parking spot is full or not found !");
        }
    }

    public static String vipParkingSelector(String spotName) throws Exception {
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            ParkingSpot parkingSpot = parkingSpotDa.findByName(spotName);
            if (parkingSpot != null && parkingSpot.isStatus()){
                parkingSpotDa.vipParkingSelector(spotName);
                return spotName;
            }
            throw new NoContentException("The selected parking spot is full or not found !");
        }
    }

    public static String unloadParkingSpot(String spotName) throws Exception{
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            ParkingSpot parkingSpot = parkingSpotDa.findByName(spotName);
            if (!(parkingSpot.isStatus())){
                parkingSpotDa.unloadParkingSpot(spotName);
                return spotName;
            }
            throw new NoContentException("The selected parking spot is not full or not found !");
        }
    }

    public static String spotPicker(String spotName) throws Exception{
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            ParkingSpot parkingSpot = parkingSpotDa.findByName(spotName);
            if (parkingSpot != null){
                parkingSpotDa.spotPicker(spotName);
                return spotName;
            }
            throw new NoContentException("The selected parking spot is not found !");
        }
    }

    public static List<String> findAllNames() throws Exception{
        try (ParkingSpotDa parkingSpotDa = new ParkingSpotDa()) {
            List<String> spotNames = parkingSpotDa.findAllNames();
            if (spotNames.size()>0){
                return spotNames;
            }
            throw new NoContentException("The list is empty !");
        }
    }

}
