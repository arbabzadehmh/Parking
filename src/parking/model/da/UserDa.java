package parking.model.da;

import parking.model.entity.User;
import parking.model.utils.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDa implements AutoCloseable{
    private Connection connection;
    private PreparedStatement statement;

    public UserDa() throws Exception{
        connection = Jdbc.getConnection();
    }

    public User save(User user) throws Exception{
        user.setId(Jdbc.nextId("USER_PARKING_SEQ"));
        statement = connection.prepareStatement(
                "INSERT INTO USER_PARKING_TBL(ID, NAME, FAMILY, USERNAME, PASSWORD, NATIONAL_CODE, STATUS, DELETED) VALUES (?,?,?,?,?,?,?,?)"
        );
        statement.setLong(1,user.getId());
        statement.setString(2, user.getName());
        statement.setString(3, user.getFamily());
        statement.setString(4, user.getUserName());
        statement.setString(5, user.getPassword());
        statement.setString(6, user.getNationalCode());
        statement.setBoolean(7, user.isStatus());
        statement.setBoolean(8,false);
        statement.execute();
        return user;
    }

    public User edit(User user) throws Exception{
        statement = connection.prepareStatement(
                "UPDATE USER_PARKING_TBL SET NAME=?, FAMILY=?, USERNAME=?, PASSWORD=?, NATIONAL_CODE=?, STATUS=?, DELETED=? WHERE ID=?"
        );
        statement.setString(1, user.getName());
        statement.setString(2, user.getFamily());
        statement.setString(3, user.getUserName());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getNationalCode());
        statement.setBoolean(6, user.isStatus());
        statement.setBoolean(7, user.isDeleted());
        statement.setLong(8, user.getId());
        statement.execute();
        return user;
    }

    public void remove(Long id)throws Exception{
        statement = connection.prepareStatement(
                "UPDATE USER_PARKING_TBL SET DELETED=1 WHERE ID=?"
        );
        statement.setLong(1, id);
        statement.execute();
    }

    public List<User> findAll() throws Exception{
        statement = connection.prepareStatement(
                "SELECT  * FROM USER_PARKING_TBL WHERE DELETED=0 ORDER BY ID"
        );
        ResultSet resultSet = statement.executeQuery();
        List<User> userList = new ArrayList<>();

        while (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .userName(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .nationalCode(resultSet.getString("NATIONAL_CODE"))
                            .status(resultSet.getBoolean("STATUS"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();

            userList.add(user);
        }
        return userList;
    }

    public User findById(Long id) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM USER_PARKING_TBL WHERE ID=? AND DELETED=0"
        );
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        User user = null;

        if (resultSet.next()){
            user =
                    User
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .userName(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .nationalCode(resultSet.getString("NATIONAL_CODE"))
                            .status(resultSet.getBoolean("STATUS"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();

        }
        return user;
    }

    public User findByUserName(String userName) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM USER_PARKING_TBL WHERE USERNAME=? AND DELETED=0"
        );
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        User user = null;

        if (resultSet.next()){
            user =
                    User
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .userName(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .nationalCode(resultSet.getString("NATIONAL_CODE"))
                            .status(resultSet.getBoolean("STATUS"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();

        }
        return user;
    }

    public List<User> findByName(String name) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM USER_PARKING_TBL WHERE NAME LIKE ? AND DELETED=0"
        );
        statement.setString(1, name + "%");
        ResultSet resultSet = statement.executeQuery();
        List<User> userList = new ArrayList<>();

        while (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .userName(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .nationalCode(resultSet.getString("NATIONAL_CODE"))
                            .status(resultSet.getBoolean("STATUS"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
            userList.add(user);
        }
        return userList;
    }

    public List<User> findByFamily(String family) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM USER_PARKING_TBL WHERE FAMILY LIKE ? AND DELETED=0"
        );
        statement.setString(1, family + "%");
        ResultSet resultSet = statement.executeQuery();
        List<User> userList = new ArrayList<>();

        while (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .userName(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .nationalCode(resultSet.getString("NATIONAL_CODE"))
                            .status(resultSet.getBoolean("STATUS"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
            userList.add(user);
        }
        return userList;
    }

    public User findByUserNameAndPassword(String userName, String password) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM USER_PARKING_TBL WHERE USERNAME=? AND PASSWORD=? AND DELETED=0 AND STATUS=1"
        );
        statement.setString(1, userName);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        User user = null;

        if (resultSet.next()){
            user =
                    User
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .userName(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .nationalCode(resultSet.getString("NATIONAL_CODE"))
                            .status(resultSet.getBoolean("STATUS"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
        }
        return user;
    }

    public List<User> findByStatus(boolean status) throws Exception{
        statement = connection.prepareStatement(
                "SELECT * FROM USER_PARKING_TBL WHERE STATUS=? AND DELETED=0"
        );
        statement.setBoolean(1, status);
        ResultSet resultSet = statement.executeQuery();
        List<User> userList = new ArrayList<>();

        while (resultSet.next()){
            User user =
                    User
                            .builder()
                            .id(resultSet.getLong("ID"))
                            .name(resultSet.getString("NAME"))
                            .family(resultSet.getString("FAMILY"))
                            .userName(resultSet.getString("USERNAME"))
                            .password(resultSet.getString("PASSWORD"))
                            .nationalCode(resultSet.getString("NATIONAL_CODE"))
                            .status(resultSet.getBoolean("STATUS"))
                            .deleted(resultSet.getBoolean("DELETED"))
                            .build();
            userList.add(user);
        }
        return userList;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}
