package parking.model.da;


import parking.model.entity.CostRate;
import parking.model.utils.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CostRateDa implements AutoCloseable{

    private Connection connection;
    private PreparedStatement statement;

    public CostRateDa() throws Exception {
        connection = Jdbc.getConnection();
    }

    public CostRate save(CostRate costRate) throws Exception {
        statement = connection.prepareStatement(
                "INSERT INTO PARKING_COST_RATE (ID, HOURLY_RATE, DAILY_RATE, HOLIDAYS_HOURLY_RATE, ENTRANCE_FEE) VALUES (?,?,?,?,?)"
        );
        statement.setLong(1,costRate.getId());
        statement.setDouble(2,costRate.getHourlyRate());
        statement.setDouble(3,costRate.getDailyRate());
        statement.setDouble(4,costRate.getHolidaysHourlyRate());
        statement.setDouble(5,costRate.getEntranceFee());
        statement.execute();
        return costRate;
    }

    public CostRate edit(CostRate costRate) throws Exception {
        statement = connection.prepareStatement(
                "UPDATE PARKING_COST_RATE SET HOURLY_RATE=?, DAILY_RATE=?, HOLIDAYS_HOURLY_RATE=?, ENTRANCE_FEE=? WHERE ID=1"
        );
        statement.setDouble(1,costRate.getHourlyRate());
        statement.setDouble(2,costRate.getDailyRate());
        statement.setDouble(3,costRate.getHolidaysHourlyRate());
        statement.setDouble(4,costRate.getEntranceFee());
        statement.execute();
        return costRate;
    }

    public List<CostRate> findAll() throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_COST_RATE ORDER BY ID"
        );
        ResultSet resultSet = statement.executeQuery();
        List<CostRate> costRates = new ArrayList<>();
        while (resultSet.next()) {
            CostRate costRate =
                    CostRate
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .hourlyRate(resultSet.getDouble("HOURLY_RATE"))
                            .dailyRate(resultSet.getDouble("DAILY_RATE"))
                            .holidaysHourlyRate(resultSet.getDouble("HOLIDAYS_HOURLY_RATE"))
                            .entranceFee(resultSet.getDouble("ENTRANCE_FEE"))
                            .build();
            costRates.add(costRate);
        }
        return costRates;
    }

    public CostRate find() throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM PARKING_COST_RATE WHERE ID=1"
        );
        ResultSet resultSet = statement.executeQuery();
        CostRate costRate = null;
        if (resultSet.next()) {
            costRate =
                    CostRate
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .hourlyRate(resultSet.getDouble("HOURLY_RATE"))
                            .dailyRate(resultSet.getDouble("DAILY_RATE"))
                            .holidaysHourlyRate(resultSet.getDouble("HOLIDAYS_HOURLY_RATE"))
                            .entranceFee(resultSet.getDouble("ENTRANCE_FEE"))
                            .build();
        }
        return costRate;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}
