package parking.model.da;

import parking.model.entity.Car;
import parking.model.entity.ParkingEntrance;
import parking.model.entity.ParkingSpot;
import parking.model.utils.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingEntranceDa implements AutoCloseable {

    private Connection connection;
    private PreparedStatement statement;

    public ParkingEntranceDa() throws Exception {
        connection = Jdbc.getConnection();
    }

    public ParkingEntrance save(ParkingEntrance parkingEntrance) throws Exception {
        parkingEntrance.setId(Jdbc.nextId("PARKING_ENTRANCE_SEQ"));
        statement = connection.prepareStatement(
                "INSERT INTO PARKING_ENTRANCE_TBL (ID, CAR_ID, ENTRANCE_TIME, PARKING_SPOT_ID, DELETED) VALUES (?,?,?,?,?)"
        );
        statement.setLong(1, parkingEntrance.getId());
        statement.setLong(2, parkingEntrance.getCar().getId());
        statement.setTimestamp(3, Timestamp.valueOf(parkingEntrance.getEntranceTime()));
        statement.setLong(4, parkingEntrance.getParkingSpot().getId());
        statement.setBoolean(5, false);
        statement.execute();
        return parkingEntrance;
    }

    public ParkingEntrance edit(ParkingEntrance parkingEntrance) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE PARKING_ENTRANCE_TBL SET CAR_ID=?, ENTRANCE_TIME=?, PARKING_SPOT_ID=? WHERE ID=?"

        );
        statement.setLong(1, parkingEntrance.getCar().getId());
        statement.setTimestamp(2, Timestamp.valueOf(parkingEntrance.getEntranceTime()));
        statement.setLong(3, parkingEntrance.getParkingSpot().getId());
        statement.setLong(4, parkingEntrance.getId());
        statement.execute();
        return parkingEntrance;
    }

    public void remove(Long id) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE PARKING_ENTRANCE_TBL SET DELETED=1 WHERE ID=?"
        );
        statement.setLong(1, id);
        statement.execute();
    }

    public List<ParkingEntrance> findAll() throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_ENTRANCE_REPORT WHERE E_DELETED=0 ORDER BY E_ENTIME"
        );
        ResultSet resultSet = statement.executeQuery();
        List<ParkingEntrance> parkingEntranceList = new ArrayList<>();

        while (resultSet.next()) {
            Car car =
                    Car
                            .builder()
                            .id(resultSet.getLong("C_ID"))
                            .carNumber(resultSet.getString("C_NUMBER"))
                            .vip(resultSet.getBoolean("C_VIP"))
                            .deleted(resultSet.getBoolean("C_DELETED"))
                            .build();

            ParkingSpot parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("PS_ID"))
                            .name(resultSet.getString("PS_NAME"))
                            .status(resultSet.getBoolean("PS_STATUS"))
                            .vipParking(resultSet.getBoolean("PS_VIP"))
                            .deleted(resultSet.getBoolean("PS_DELETED"))
                            .build();

            ParkingEntrance parkingEntrance =
                    ParkingEntrance
                            .builder()
                            .id(resultSet.getLong("E_ID"))
                            .car(car)
                            .parkingSpot(parkingSpot)
                            .entranceTime(resultSet.getTimestamp("E_ENTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("E_DELETED"))
                            .build();

            parkingEntranceList.add(parkingEntrance);
        }
        return parkingEntranceList;
    }

    public ParkingEntrance findById(Long id) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_ENTRANCE_REPORT WHERE E_ID=? AND E_DELETED=0"
        );
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        ParkingEntrance parkingEntrance = null;

        if (resultSet.next()) {
            Car car =
                    Car
                            .builder()
                            .id(resultSet.getLong("C_ID"))
                            .carNumber(resultSet.getString("C_NUMBER"))
                            .vip(resultSet.getBoolean("C_VIP"))
                            .deleted(resultSet.getBoolean("C_DELETED"))
                            .build();

            ParkingSpot parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("PS_ID"))
                            .name(resultSet.getString("PS_NAME"))
                            .status(resultSet.getBoolean("PS_STATUS"))
                            .vipParking(resultSet.getBoolean("PS_VIP"))
                            .deleted(resultSet.getBoolean("PS_DELETED"))
                            .build();

            parkingEntrance =
                    ParkingEntrance
                            .builder()
                            .id(resultSet.getLong("E_ID"))
                            .car(car)
                            .parkingSpot(parkingSpot)
                            .entranceTime(resultSet.getTimestamp("E_ENTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("E_DELETED"))
                            .build();

        }
        return parkingEntrance;
    }

    public List<ParkingEntrance> findByCarNumber(String carNumber) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_ENTRANCE_REPORT WHERE C_NUMBER=? AND E_DELETED=0"
        );
        statement.setString(1, carNumber);
        ResultSet resultSet = statement.executeQuery();
        List<ParkingEntrance> parkingEntranceList = new ArrayList<>();

        while (resultSet.next()) {
            Car car =
                    Car
                            .builder()
                            .id(resultSet.getLong("C_ID"))
                            .carNumber(resultSet.getString("C_NUMBER"))
                            .vip(resultSet.getBoolean("C_VIP"))
                            .deleted(resultSet.getBoolean("C_DELETED"))
                            .build();

            ParkingSpot parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("PS_ID"))
                            .name(resultSet.getString("PS_NAME"))
                            .status(resultSet.getBoolean("PS_STATUS"))
                            .vipParking(resultSet.getBoolean("PS_VIP"))
                            .deleted(resultSet.getBoolean("PS_DELETED"))
                            .build();

            ParkingEntrance parkingEntrance =
                    ParkingEntrance
                            .builder()
                            .id(resultSet.getLong("E_ID"))
                            .car(car)
                            .parkingSpot(parkingSpot)
                            .entranceTime(resultSet.getTimestamp("E_ENTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("E_DELETED"))
                            .build();

            parkingEntranceList.add(parkingEntrance);
        }
        return parkingEntranceList;
    }

    public List<ParkingEntrance> findByEntranceTime(LocalDateTime entranceTime) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_ENTRANCE_REPORT WHERE E_ENTIME = ? AND E_DELETED=0"
        );
        statement.setTimestamp(1, Timestamp.valueOf(entranceTime));
        ResultSet resultSet = statement.executeQuery();
        List<ParkingEntrance> entranceList = new ArrayList<>();

        while (resultSet.next()){
            Car car =
                    Car
                            .builder()
                            .id(resultSet.getLong("C_ID"))
                            .carNumber(resultSet.getString("C_NUMBER"))
                            .vip(resultSet.getBoolean("C_VIP"))
                            .deleted(resultSet.getBoolean("C_DELETED"))
                            .build();

            ParkingSpot parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("PS_ID"))
                            .name(resultSet.getString("PS_NAME"))
                            .status(resultSet.getBoolean("PS_STATUS"))
                            .vipParking(resultSet.getBoolean("PS_VIP"))
                            .deleted(resultSet.getBoolean("PS_DELETED"))
                            .build();

            ParkingEntrance parkingEntrance =
                    ParkingEntrance
                            .builder()
                            .id(resultSet.getLong("E_ID"))
                            .car(car)
                            .parkingSpot(parkingSpot)
                            .entranceTime(resultSet.getTimestamp("E_ENTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("E_DELETED"))
                            .build();

            entranceList.add(parkingEntrance);
        }
        return entranceList;
    }

    public List<ParkingEntrance> findBySpotName(String spotName) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_ENTRANCE_REPORT WHERE PS_NAME=? AND E_DELETED=0"
        );
        statement.setString(1, spotName);
        ResultSet resultSet = statement.executeQuery();
        List<ParkingEntrance> parkingEntranceList = new ArrayList<>();

        while (resultSet.next()){
            Car car =
                    Car
                            .builder()
                            .id(resultSet.getLong("C_ID"))
                            .carNumber(resultSet.getString("C_NUMBER"))
                            .vip(resultSet.getBoolean("C_VIP"))
                            .deleted(resultSet.getBoolean("C_DELETED"))
                            .build();

            ParkingSpot parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("PS_ID"))
                            .name(resultSet.getString("PS_NAME"))
                            .status(resultSet.getBoolean("PS_STATUS"))
                            .vipParking(resultSet.getBoolean("PS_VIP"))
                            .deleted(resultSet.getBoolean("PS_DELETED"))
                            .build();

            ParkingEntrance parkingEntrance =
                    ParkingEntrance
                            .builder()
                            .id(resultSet.getLong("E_ID"))
                            .car(car)
                            .parkingSpot(parkingSpot)
                            .entranceTime(resultSet.getTimestamp("E_ENTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("E_DELETED"))
                            .build();

            parkingEntranceList.add(parkingEntrance);
        }
        return parkingEntranceList;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}