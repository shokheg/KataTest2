package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.management.relation.Role;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String SQL_ADD_USER = "INSERT INTO user (name,lastName,age) values(?,?,?)";
    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS USER";
    private static final String CLEAN_USERS_TABLE = "TRUNCATE TABLE USER";
    private static final String REMOVE_USER_BY_ID = "DELETE from user WHERE id=?";
    private static final String FIND_ALL_USERS = "SELECT * from user";
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS USER " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " name VARCHAR(255), " +
            " lastName VARCHAR(255), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.getConn(); Statement stmt = connection.createStatement();
        ) {
            String sql = CREATE_USERS_TABLE;
            stmt.executeUpdate(sql);
            System.out.println("Created table Users in given database...");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConn(); Statement stmt = connection.createStatement();
        ) {
            String sql = DROP_USERS_TABLE;
            stmt.executeUpdate(sql);
            System.out.println("Table Users in given database is dropped");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConn(); PreparedStatement stmt = connection.prepareStatement(SQL_ADD_USER);
        ) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setInt(3, age);
            int checkOperation = stmt.executeUpdate();
            if (checkOperation != 0) {
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            } else {
                System.out.println("User was not added");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConn(); PreparedStatement stmt = connection.prepareStatement(REMOVE_USER_BY_ID);
        ) {
            stmt.setInt(1, (int) id);
            int checkOperation = stmt.executeUpdate();
            if (checkOperation != 0) {
                System.out.println("User deleted");
            } else {
                System.out.println("User was not deleted");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConn(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);) {
            while (resultSet.next()){
                list.add(createUser(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConn(); Statement stmt = connection.createStatement();
        ) {
            String sql = CLEAN_USERS_TABLE;
            stmt.executeUpdate(sql);
            System.out.println("Table Users is cleaned");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private User createUser(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String lastName = resultSet.getString("lastName");
        Byte age = resultSet.getByte("age");
        User user = new User();
                user.setId(userId);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
        return user;
    }




}
