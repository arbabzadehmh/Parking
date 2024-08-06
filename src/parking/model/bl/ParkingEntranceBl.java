package parking.model.bl;

import parking.controller.exception.NoContentException;
import parking.model.da.ParkingEntranceDa;
import parking.model.entity.ParkingEntrance;

import java.time.LocalDateTime;
import java.util.List;

public class ParkingEntranceBl {

    public static ParkingEntrance save(ParkingEntrance parkingEntrance) throws Exception {
        try (ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa()) {
            return parkingEntranceDa.save(parkingEntrance);
        }
    }

    public static ParkingEntrance edit(ParkingEntrance parkingEntrance) throws Exception {
        try (ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa()) {
            if (parkingEntranceDa.findById(parkingEntrance.getId()) != null) {
                return parkingEntranceDa.edit(parkingEntrance);
            }
            throw new NoContentException("Entrance is not in the list !");
        }
    }

    public static ParkingEntrance remove(Long id) throws Exception {
        try (ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa()) {
            ParkingEntrance parkingEntrance = parkingEntranceDa.findById(id);
            if (parkingEntrance != null) {
                parkingEntranceDa.remove(id);
                return parkingEntrance;
            }
            throw new NoContentException("Entrance is not in the list !");
        }
    }

    public static List<ParkingEntrance> findAll() throws Exception {
        try (ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa()) {
            List<ParkingEntrance> parkingEntranceList = parkingEntranceDa.findAll();
            if (parkingEntranceList.size() > 0) {
                return parkingEntranceList;
            }
            throw new NoContentException("The list is empty !");
        }
    }

    public static ParkingEntrance findById(Long id) throws Exception {
        try (ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa()) {
            ParkingEntrance parkingEntrance = parkingEntranceDa.findById(id);
            if (parkingEntrance != null) {
                return parkingEntrance;
            }
            throw new NoContentException("No Entrance was found !");
        }
    }

    public static List<ParkingEntrance> findByCarNumber(String carNumber) throws Exception {
        try (ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa()) {
            List<ParkingEntrance> parkingEntranceList = parkingEntranceDa.findByCarNumber(carNumber);
            if (parkingEntranceList.size() > 0) {
                return parkingEntranceList;
            }
            throw new NoContentException("No Entrance was found !");
        }
    }

    public static List<ParkingEntrance> findByEntranceTime(LocalDateTime entranceTime) throws Exception {
        try (ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa()) {
            List<ParkingEntrance> parkingEntranceList = parkingEntranceDa.findByEntranceTime(entranceTime);
            if (parkingEntranceList.size()>0){
                return parkingEntranceList;
            }
            throw new NoContentException("No Entrance was found !");
        }
    }

    public static List<ParkingEntrance> findBySpotName(String spotName) throws Exception{
        try(ParkingEntranceDa parkingEntranceDa = new ParkingEntranceDa()) {
            List<ParkingEntrance> parkingEntranceList = parkingEntranceDa.findBySpotName(spotName);
            if (parkingEntranceList.size()>0){
                return parkingEntranceList;
            }
            throw new NoContentException("No Entrance was found !");
        }
    }

}
