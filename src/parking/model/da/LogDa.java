package parking.model.da;

import parking.model.entity.Log;
import parking.model.entity.User;
import parking.model.utils.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogDa implements AutoCloseable{

    private PreparedStatement statement;
    private Connection connection;

    public LogDa() throws Exception {
        connection = Jdbc.getConnection();
    }

    public Log save(Log log) throws Exception{
        log.setId(Jdbc.nextId("LOG_PARKING_SEQ"));
        statement = connection.prepareStatement(
                "INSERT INTO LOG_PARKING_TBL (ID, ACTION, DATA, USER_ID, LOG_TIMESTAMP) VALUES (?,?,?,?,?)"
        );
        statement.setLong(1, log.getId());
        statement.setString(2, log.getAction());
        statement.setString(3, log.getData());
        statement.setLong(4, log.getUser().getId());
        statement.setTimestamp(5, Timestamp.valueOf(log.getLogTimeStamp()));
        statement.execute();
        return log;
    }

    public List<Log> findAll() throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM LOG_REPORT ORDER BY LOG_TIMESTAMP"
        );
        ResultSet resultSet = statement.executeQuery();
        List<Log> logList = new ArrayList<>();

        while (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("USER_ID"))
                            .name(resultSet.getString("U_NAME"))
                            .family(resultSet.getString("U_FAMILY"))
                            .userName(resultSet.getString("U_USERNAME"))
                            .password(resultSet.getString("U_PASSWORD"))
                            .nationalCode(resultSet.getString("U_NATIONAL_CODE"))
                            .status(resultSet.getBoolean("U_STATUS"))
                            .deleted(resultSet.getBoolean("U_DELETED"))
                            .build();

            Log log =
                    Log
                            .builder()
                            .id(resultSet.getLong("L_ID"))
                            .action(resultSet.getString("ACTION"))
                            .data(resultSet.getString("DATA"))
                            .user(user)
                            .logTimeStamp(resultSet.getTimestamp("LOG_TIMESTAMP").toLocalDateTime())
                            .build();
            logList.add(log);
        }
        return logList;
    }

    public Log findById(Long id) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM LOG_REPORT WHERE L_ID = ?"
        );
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Log log = null;

        if (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("USER_ID"))
                            .name(resultSet.getString("U_NAME"))
                            .family(resultSet.getString("U_FAMILY"))
                            .userName(resultSet.getString("U_USERNAME"))
                            .password(resultSet.getString("U_PASSWORD"))
                            .nationalCode(resultSet.getString("U_NATIONAL_CODE"))
                            .status(resultSet.getBoolean("U_STATUS"))
                            .deleted(resultSet.getBoolean("U_DELETED"))
                            .build();

            log =
                    Log
                            .builder()
                            .id(resultSet.getLong("L_ID"))
                            .action(resultSet.getString("ACTION"))
                            .data(resultSet.getString("DATA"))
                            .user(user)
                            .logTimeStamp(resultSet.getTimestamp("LOG_TIMESTAMP").toLocalDateTime())
                            .build();
        }
        return log;
    }

    public List<Log> findByUserId(long id) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM LOG_REPORT WHERE USER_ID=?"
        );
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        List<Log> logList = new ArrayList<>();

        while (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("USER_ID"))
                            .name(resultSet.getString("U_NAME"))
                            .family(resultSet.getString("U_FAMILY"))
                            .userName(resultSet.getString("U_USERNAME"))
                            .password(resultSet.getString("U_PASSWORD"))
                            .nationalCode(resultSet.getString("U_NATIONAL_CODE"))
                            .status(resultSet.getBoolean("U_STATUS"))
                            .deleted(resultSet.getBoolean("U_DELETED"))
                            .build();

            Log log =
                    Log
                            .builder()
                            .id(resultSet.getLong("L_ID"))
                            .action(resultSet.getString("ACTION"))
                            .data(resultSet.getString("DATA"))
                            .user(user)
                            .logTimeStamp(resultSet.getTimestamp("LOG_TIMESTAMP").toLocalDateTime())
                            .build();
            logList.add(log);
        }
        return logList;
    }

    public List<Log> findByAction(String action) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM LOG_REPORT WHERE ACTION = ?"
        );
        statement.setString(1, action);
        ResultSet resultSet = statement.executeQuery();
        List<Log> logList = new ArrayList<>();

        while (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("USER_ID"))
                            .name(resultSet.getString("U_NAME"))
                            .family(resultSet.getString("U_FAMILY"))
                            .userName(resultSet.getString("U_USERNAME"))
                            .password(resultSet.getString("U_PASSWORD"))
                            .nationalCode(resultSet.getString("U_NATIONAL_CODE"))
                            .status(resultSet.getBoolean("U_STATUS"))
                            .deleted(resultSet.getBoolean("U_DELETED"))
                            .build();

            Log log =
                    Log
                            .builder()
                            .id(resultSet.getLong("L_ID"))
                            .action(resultSet.getString("ACTION"))
                            .data(resultSet.getString("DATA"))
                            .user(user)
                            .logTimeStamp(resultSet.getTimestamp("LOG_TIMESTAMP").toLocalDateTime())
                            .build();
            logList.add(log);
        }
        return logList;
    }

    public List<Log> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM LOG_REPORT WHERE LOG_TIMESTAMP BETWEEN ? AND ?"
        );
        statement.setTimestamp(1, Timestamp.valueOf(startTime));
        statement.setTimestamp(2, Timestamp.valueOf(endTime));
        ResultSet resultSet = statement.executeQuery();
        List<Log> logList = new ArrayList<>();

        while (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("USER_ID"))
                            .name(resultSet.getString("U_NAME"))
                            .family(resultSet.getString("U_FAMILY"))
                            .userName(resultSet.getString("U_USERNAME"))
                            .password(resultSet.getString("U_PASSWORD"))
                            .nationalCode(resultSet.getString("U_NATIONAL_CODE"))
                            .status(resultSet.getBoolean("U_STATUS"))
                            .deleted(resultSet.getBoolean("U_DELETED"))
                            .build();

            Log log =
                    Log
                            .builder()
                            .id(resultSet.getLong("L_ID"))
                            .action(resultSet.getString("ACTION"))
                            .data(resultSet.getString("DATA"))
                            .user(user)
                            .logTimeStamp(resultSet.getTimestamp("LOG_TIMESTAMP").toLocalDateTime())
                            .build();
            logList.add(log);
        }
        return logList;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}
