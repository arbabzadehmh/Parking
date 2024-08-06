package parking.model.da;

import parking.model.entity.Car;
import parking.model.utils.Jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarDa implements AutoCloseable {

    private Connection connection;
    private PreparedStatement statement;

    public CarDa() throws Exception {
        connection = Jdbc.getConnection();
    }

    public Car save(Car car) throws Exception {
        car.setId(Jdbc.nextId("CAR_SEQ"));
        statement = connection.prepareStatement(
                "INSERT INTO CAR_TBL(ID,CAR_NUMBER,VIP,DELETED) VALUES (?,?,?,?)"
        );
        statement.setLong(1, car.getId());
        statement.setString(2, car.getCarNumber());
        statement.setBoolean(3, car.isVip());
        statement.setBoolean(4, false);
        statement.execute();
        return car;
    }

    public Car edit(Car car) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE CAR_TBL SET CAR_NUMBER=?, VIP=? WHERE ID=?"
        );
        statement.setString(1, car.getCarNumber());
        statement.setBoolean(2, car.isVip());
        statement.setLong(3, car.getId());
        statement.execute();
        return car;
    }

    public Long remove(Long id) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE CAR_TBL SET DELETED=1 WHERE ID=?"
        );
        statement.setLong(1, id);
        statement.execute();
        return id;
    }

    public List<Car> findAll() throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM CAR_TBL WHERE DELETED=0 ORDER BY ID"
        );
        ResultSet resultSet = statement.executeQuery();
        List<Car> carList = new ArrayList<>();
        while (resultSet.next()) {
            Car car =
                    Car
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .carNumber(resultSet.getString("CAR_NUMBER"))
                            .vip(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
            carList.add(car);
        }
        return carList;
    }

    public Car findByID(Long id) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM CAR_TBL WHERE ID=? AND DELETED=0"
        );
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Car car = null;
        if (resultSet.next()) {
            car =
                    Car
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .carNumber(resultSet.getString("CAR_NUMBER"))
                            .vip(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
        }
        return car;
    }

    public Car findByNumber(String carNumber) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM CAR_TBL WHERE CAR_NUMBER=? AND DELETED=0"
        );
        statement.setString(1, carNumber);
        ResultSet resultSet = statement.executeQuery();
        Car car = null;

        if (resultSet.next()) {
            car =
                    Car
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .carNumber(resultSet.getString("CAR_NUMBER"))
                            .vip(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();

        }
        return car;
    }

    public List<Car> findByVipStatus(boolean status) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM CAR_TBL WHERE VIP=? AND DELETED=0"
        );
        statement.setBoolean(1, status);
        ResultSet resultSet = statement.executeQuery();
        List<Car> carList = new ArrayList<>();

        while (resultSet.next()) {
            Car car =
                    Car
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .carNumber(resultSet.getString("CAR_NUMBER"))
                            .vip(resultSet.getBoolean("VIP"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
            carList.add(car);
        }
        return carList;
    }

    public Car findOrSaveByNumber(String carNumber) throws Exception{
        Car car =
                Car
                        .builder()
                        .id(Jdbc.nextId("CAR_SEQ"))
                        .carNumber(carNumber)
                        .vip(false)
                        .deleted(false)
                        .build();
        statement = connection.prepareStatement(
                "INSERT INTO CAR_TBL(ID,CAR_NUMBER,VIP,DELETED) VALUES (?,?,?,?)"
        );
        statement.setLong(1, car.getId());
        statement.setString(2, car.getCarNumber());
        statement.setBoolean(3, car.isVip());
        statement.setBoolean(4, car.isDeleted());
        statement.execute();
        return car;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}
