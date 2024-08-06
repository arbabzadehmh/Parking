package parking.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@SuperBuilder
@Setter
@Getter

public class ParkingEntranceVo {
    private long id;
    private LocalDateTime entranceTime;
    private String carNumber;
    private boolean carVIP;
    private String spotName;
    private boolean spotVIP;
    private boolean spotStatus;

    public ParkingEntranceVo(ParkingEntrance parkingEntrance) {
        this.id = parkingEntrance.getId();
        this.entranceTime = parkingEntrance.getEntranceTime();
        this.carNumber = parkingEntrance.getCar().getCarNumber();
        this.carVIP = parkingEntrance.getCar().isVip();
        this.spotName = parkingEntrance.getParkingSpot().getName();
        this.spotVIP = parkingEntrance.getParkingSpot().isVipParking();
        this.spotStatus = parkingEntrance.getParkingSpot().isStatus();
    }
}
