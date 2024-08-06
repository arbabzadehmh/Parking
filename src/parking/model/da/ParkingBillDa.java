package parking.model.da;

import parking.model.entity.Car;
import parking.model.entity.ParkingBill;
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

public class ParkingBillDa implements AutoCloseable {

    private Connection connection;
    private PreparedStatement statement;

    public ParkingBillDa() throws Exception {
        connection = Jdbc.getConnection();
    }

    public ParkingBill save(ParkingBill parkingBill) throws Exception {
        parkingBill.setId(Jdbc.nextId("PARKING_BILL_SEQ"));
        statement = connection.prepareStatement(
                "INSERT INTO PARKING_BILL_TABLE (ID, ENTRANCE_ID, EXIT_TIME, COST, DELETED) VALUES (?,?,?,?,?)"
        );
        statement.setLong(1, parkingBill.getId());
        statement.setLong(2, parkingBill.getParkingEntrance().getId());
        statement.setTimestamp(3, Timestamp.valueOf(parkingBill.getExitTime()));
        statement.setDouble(4, parkingBill.getCost());
        statement.setBoolean(5, false);
        statement.execute();
        return parkingBill;
    }

    public ParkingBill edit(ParkingBill parkingBill) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE PARKING_BILL_TABLE SET ENTRANCE_ID=?, EXIT_TIME=?, COST=? WHERE ID=?"
        );
        statement.setLong(1, parkingBill.getParkingEntrance().getId());
        statement.setTimestamp(2, Timestamp.valueOf(parkingBill.getExitTime()));
        statement.setDouble(3, parkingBill.getCost());
        statement.setLong(4, parkingBill.getId());
        statement.execute();
        return parkingBill;
    }

    public void remove(Long id) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE PARKING_BILL_TABLE SET DELETED=1 WHERE ID=?"
        );
        statement.setLong(1, id);
        statement.execute();
    }

    public List<ParkingBill> findAll() throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_BILL_REPORT WHERE B_DELETED=0 ORDER BY E_ENTIME"
        );
        ResultSet resultSet = statement.executeQuery();
        List<ParkingBill> parkingBillList = new ArrayList<>();

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

            ParkingBill parkingBill =
                    ParkingBill
                            .builder()
                            .id(resultSet.getLong("B_ID"))
                            .parkingEntrance(parkingEntrance)
                            .cost(resultSet.getDouble("B_COST"))
                            .exitTime(resultSet.getTimestamp("B_EXTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("B_DELETED"))
                            .build();


            parkingBillList.add(parkingBill);
        }
        return parkingBillList;
    }

    public ParkingBill findById(Long id) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_BILL_REPORT WHERE B_ID=? AND B_DELETED=0"
        );
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        ParkingBill parkingBill = null;
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

            ParkingEntrance parkingEntrance =
                    ParkingEntrance
                            .builder()
                            .id(resultSet.getLong("E_ID"))
                            .car(car)
                            .parkingSpot(parkingSpot)
                            .entranceTime(resultSet.getTimestamp("E_ENTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("E_DELETED"))
                            .build();

            parkingBill =
                    ParkingBill
                            .builder()
                            .id(resultSet.getLong("B_ID"))
                            .parkingEntrance(parkingEntrance)
                            .cost(resultSet.getDouble("B_COST"))
                            .exitTime(resultSet.getTimestamp("B_EXTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("B_DELETED"))
                            .build();

        }
        return parkingBill;
    }

    public List<ParkingBill> findByCarNumber(String carNumber) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_BILL_REPORT WHERE C_NUMBER=? AND B_DELETED=0"
        );
        statement.setString(1, carNumber);
        ResultSet resultSet = statement.executeQuery();
        List<ParkingBill> parkingBillList = new ArrayList<>();

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

            ParkingBill parkingBill =
                    ParkingBill
                            .builder()
                            .id(resultSet.getLong("B_ID"))
                            .parkingEntrance(parkingEntrance)
                            .cost(resultSet.getDouble("B_COST"))
                            .exitTime(resultSet.getTimestamp("B_EXTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("B_DELETED"))
                            .build();


            parkingBillList.add(parkingBill);
        }
        return parkingBillList;
    }

    public void costCalculator(ParkingBill parkingBill) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE PARKING_BILL_TABLE SET COST=? WHERE ID=?"
        );
        statement.setDouble(1, parkingBill.getCost());
        statement.setLong(2, parkingBill.getId());
        statement.execute();
    }

    public List<ParkingBill> findByExitTime(LocalDateTime exitTime) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_BILL_REPORT WHERE B_EXTIME=? AND B_DELETED=0"
        );
        statement.setTimestamp(1, Timestamp.valueOf(exitTime));
        ResultSet resultSet = statement.executeQuery();
        List<ParkingBill> parkingBillList = new ArrayList<>();

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


            ParkingBill parkingBill =
                    ParkingBill
                            .builder()
                            .id(resultSet.getLong("B_ID"))
                            .parkingEntrance(parkingEntrance)
                            .cost(resultSet.getDouble("B_COST"))
                            .exitTime(resultSet.getTimestamp("B_EXTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("B_DELETED"))
                            .build();


            parkingBillList.add(parkingBill);
        }
        return parkingBillList;
    }

    public ParkingBill findByEntranceId(Long entId) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_BILL_REPORT WHERE E_ID=? AND B_DELETED=0"
        );
        statement.setLong(1, entId);
        ResultSet resultSet = statement.executeQuery();
        ParkingBill parkingBill = null;

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

            ParkingEntrance parkingEntrance =
                    ParkingEntrance
                            .builder()
                            .id(resultSet.getLong("E_ID"))
                            .car(car)
                            .parkingSpot(parkingSpot)
                            .entranceTime(resultSet.getTimestamp("E_ENTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("E_DELETED"))
                            .build();

            parkingBill =
                    ParkingBill
                            .builder()
                            .id(resultSet.getLong("B_ID"))
                            .parkingEntrance(parkingEntrance)
                            .cost(resultSet.getDouble("B_COST"))
                            .exitTime(resultSet.getTimestamp("B_EXTIME").toLocalDateTime())
                            .deleted(resultSet.getBoolean("B_DELETED"))
                            .build();

        }
        return parkingBill;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}
