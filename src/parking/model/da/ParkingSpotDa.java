package parking.model.da;

import parking.model.entity.ParkingSpot;
import parking.model.utils.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpotDa implements AutoCloseable{

    private Connection connection;
    private PreparedStatement statement;

    public ParkingSpotDa() throws Exception {
        connection = Jdbc.getConnection();
    }

    public ParkingSpot save(ParkingSpot parkingSpot) throws Exception{
        parkingSpot.setId(Jdbc.nextId("PARKING_SPOT_SEQ"));
        statement = connection.prepareStatement(
                "INSERT INTO PARKING_SPOT_TBL (ID, SPOT_NAME, STATUS, VIP, DELETED) VALUES (?,?,?,?,?)"
        );
        statement.setLong(1, parkingSpot.getId());
        statement.setString(2, parkingSpot.getName());
        statement.setBoolean(3, parkingSpot.isStatus());
        statement.setBoolean(4, parkingSpot.isVipParking());
        statement.setBoolean(5, false);
        statement.execute();
        return parkingSpot;
    }

    public ParkingSpot edit(ParkingSpot parkingSpot) throws Exception{
        statement = connection.prepareStatement(
                "UPDATE PARKING_SPOT_TBL SET SPOT_NAME=?, STATUS=?, VIP=? WHERE ID=?"
        );
        statement.setString(1, parkingSpot.getName());
        statement.setBoolean(2, parkingSpot.isStatus());
        statement.setBoolean(3, parkingSpot.isVipParking());
        statement.setLong(4, parkingSpot.getId());
        statement.execute();
        return parkingSpot;
    }

    public void remove(Long id) throws Exception{
        statement = connection.prepareStatement(
                "UPDATE PARKING_SPOT_TBL SET DELETED=1 WHERE ID=?"
        );
        statement.setLong(1,id);
        statement.execute();
    }

    public List<ParkingSpot> findAll() throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_SPOT_TBL WHERE DELETED=0 ORDER BY STATUS"
        );
        ResultSet resultSet = statement.executeQuery();
        List<ParkingSpot> parkingSpotList = new ArrayList<>();

        while (resultSet.next()){
            ParkingSpot parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("SPOT_NAME"))
                            .status(resultSet.getBoolean("STATUS"))
                            .vipParking(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
            parkingSpotList.add(parkingSpot);
        }
        return parkingSpotList;
    }

    public ParkingSpot findById(Long id) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_SPOT_TBL WHERE ID=? AND DELETED=0"
        );
        statement.setLong(1,id);
        ResultSet resultSet = statement.executeQuery();
        ParkingSpot parkingSpot = null;

        if (resultSet.next()){
            parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("SPOT_NAME"))
                            .status(resultSet.getBoolean("STATUS"))
                            .vipParking(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
        }
        return parkingSpot;
    }

    public ParkingSpot findByName(String name) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_SPOT_TBL WHERE SPOT_NAME=? AND DELETED=0"
        );
        statement.setString(1,name);
        ResultSet resultSet = statement.executeQuery();
        ParkingSpot parkingSpot = null;

        if (resultSet.next()){
            parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("SPOT_NAME"))
                            .status(resultSet.getBoolean("STATUS"))
                            .vipParking(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
        }
        return parkingSpot;
    }

    public List<ParkingSpot> findByStatus(boolean status) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_SPOT_TBL WHERE STATUS=? AND DELETED=0"
        );
        statement.setBoolean(1,status);
        ResultSet resultSet = statement.executeQuery();
        List<ParkingSpot> parkingSpotList = new ArrayList<>();

        while (resultSet.next()){
            ParkingSpot parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("SPOT_NAME"))
                            .status(resultSet.getBoolean("STATUS"))
                            .vipParking(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
            parkingSpotList.add(parkingSpot);
        }
        return parkingSpotList;
    }

    public List<ParkingSpot> findByVipStatus(boolean status) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_SPOT_TBL WHERE VIP=? AND DELETED=0"
        );
        statement.setBoolean(1,status);
        ResultSet resultSet = statement.executeQuery();
        List<ParkingSpot> parkingSpotList = new ArrayList<>();

        while (resultSet.next()){
            ParkingSpot parkingSpot =
                    ParkingSpot
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("SPOT_NAME"))
                            .status(resultSet.getBoolean("STATUS"))
                            .vipParking(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
            parkingSpotList.add(parkingSpot);
        }
        return parkingSpotList;
    }

    public int remainingCapacityOfGeneralParking() throws Exception{
        statement = connection.prepareStatement(
                "SELECT COUNT(ID) FROM PARKING_SPOT_TBL WHERE STATUS=1 AND VIP=0 AND DELETED=0"
        );
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public int remainingCapacityOfVipParking() throws Exception{
        statement = connection.prepareStatement(
                "SELECT COUNT(ID) FROM PARKING_SPOT_TBL WHERE STATUS=1 AND VIP=1 AND DELETED=0"
        );
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public List<String> findEmptyGeneralSpot() throws Exception{
        statement = connection.prepareStatement(
                "SELECT SPOT_NAME FROM PARKING_SPOT_TBL WHERE STATUS=1 AND VIP=0 AND DELETED=0"
        );
        ResultSet resultSet = statement.executeQuery();
        List<String> emptyGeneralSpots = new ArrayList<>();

        while (resultSet.next()){
            String spot = resultSet.getString("SPOT_NAME");
            emptyGeneralSpots.add(spot);
        }
        return emptyGeneralSpots;
    }

    public List<String> findEmptyVipSpot() throws Exception{
        statement = connection.prepareStatement(
                "SELECT SPOT_NAME FROM PARKING_SPOT_TBL WHERE STATUS=1 AND VIP=1 AND DELETED=0"
        );
        ResultSet resultSet = statement.executeQuery();
        List<String> emptyVipSpots = new ArrayList<>();

        while (resultSet.next()){
            String spot = resultSet.getString("SPOT_NAME");
            emptyVipSpots.add(spot);
        }
        return emptyVipSpots;
    }

    public List<String> findEmptySpot() throws Exception{
        statement = connection.prepareStatement(
                "SELECT SPOT_NAME FROM PARKING_SPOT_TBL WHERE STATUS=1 AND DELETED=0 ORDER BY ID"
        );
        ResultSet resultSet = statement.executeQuery();
        List<String> emptySpots = new ArrayList<>();

        while (resultSet.next()){
            String spot = resultSet.getString("SPOT_NAME");
            emptySpots.add(spot);
        }
        return emptySpots;
    }

    public void generalParkingSelector(String spotName) throws Exception{
        statement = connection.prepareStatement(
                "UPDATE PARKING_SPOT_TBL SET STATUS=0 WHERE STATUS=1 AND VIP=0 AND SPOT_NAME=? AND DELETED=0"
        );
        statement.setString(1, spotName);
        statement.execute();
    }

    public void vipParkingSelector(String spotName) throws Exception{
        statement = connection.prepareStatement(
                "UPDATE PARKING_SPOT_TBL SET STATUS=0 WHERE STATUS=1 AND SPOT_NAME=? AND DELETED=0"
        );
        statement.setString(1, spotName);
        statement.execute();
    }

    public void unloadParkingSpot(String spotName) throws Exception{
        statement = connection.prepareStatement(
                "UPDATE  PARKING_SPOT_TBL SET STATUS=1 WHERE SPOT_NAME=? AND DELETED=0"
        );
        statement.setString(1, spotName);
        statement.execute();
    }

    public void spotPicker(String spotName) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE  PARKING_SPOT_TBL SET STATUS=0 WHERE SPOT_NAME=? AND DELETED=0"
        );
        statement.setString(1, spotName);
        statement.execute();
    }

    public List<String> findAllNames() throws Exception{
        statement = connection.prepareStatement(
                "SELECT SPOT_NAME FROM PARKING_SPOT_TBL WHERE DELETED=0 ORDER BY ID"
        );
        ResultSet resultSet = statement.executeQuery();
        List<String> spotNames = new ArrayList<>();

        while (resultSet.next()){
            String spot = resultSet.getString("SPOT_NAME");
            spotNames.add(spot);
        }
        return spotNames;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }


}
