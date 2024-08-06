package parking.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@SuperBuilder
@Setter
@Getter

public class ParkingEntrance {
    private Long id;
    private Car car;
    private LocalDateTime entranceTime;
    private ParkingSpot parkingSpot;
    private boolean deleted;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
