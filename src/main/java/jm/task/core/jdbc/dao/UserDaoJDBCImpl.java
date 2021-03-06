package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() { }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "   id BIGINT NOT NULL AUTO_INCREMENT," +
                "   name VARCHAR(20)," +
                "   last_name VARCHAR(20)," +
                "   age TINYINT UNSIGNED," +
                "   primary key (id)" +
                ")";
        Connection connection = Util.getConnection();
        Statement statement = getStatement(connection);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        Connection connection = Util.getConnection();
        Statement statement = getStatement(connection);
        try {
            statement.executeUpdate(sql);
        } catch(SQLSyntaxErrorException e) {
            // Создание дубликата таблицы исключает падение программы
            if (e.getErrorCode() != 1050)
                e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USER(NAME, LAST_NAME, AGE) VALUES(?, ?, ?)";
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = getPrepareStatement(connection, sql);
        try {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            Util.closeConnection(connection);
        }
        System.out.println("User is name: " + name + " add.");
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE user.id = ?";
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = getPrepareStatement(connection, sql);
        try {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            Util.closeConnection(connection);
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM user;";
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = getPrepareStatement(connection, sql);
        try (ResultSet resultSet = preparedStatement.executeQuery(sql)){
            while(resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            Util.closeConnection(connection);
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE user";
        Connection connection = Util.getConnection();
        Statement statement = getStatement(connection);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    // Получение экземпляра Statement
    private Statement getStatement(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    // Получение экземпляра PrepareStatement
    private PreparedStatement getPrepareStatement(Connection connection, String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    // Закрытие PrepareStatement
    private void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
