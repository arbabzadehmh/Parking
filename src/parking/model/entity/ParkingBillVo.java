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
public class ParkingBillVo {

    private Long id;
    private LocalDateTime exitTime;
    private LocalDateTime entTime;
    private double cost;
    private String carNumber;
    private String spotName;
    private boolean vipCar;

    public ParkingBillVo(ParkingBill parkingBill) {

        this.id = parkingBill.getId();
        this.exitTime = parkingBill.getExitTime();
        this.entTime = parkingBill.getParkingEntrance().getEntranceTime();
        this.cost = parkingBill.getCost();
        this.carNumber = parkingBill. getParkingEntrance().getCar().getCarNumber();
        this.spotName = parkingBill.getParkingEntrance().getParkingSpot().getName();
        this.vipCar = parkingBill.getParkingEntrance().getCar().isVip();
    }
}
