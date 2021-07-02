package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();

        String sql = "CREATE TABLE if not exists USER(\n" +
                "    id       int         NOT NULL PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name     VARCHAR(50) NOT NULL,\n" +
                "    lastName VARCHAR(50) NOT NULL,\n" +
                "    age      smallint\n" +
                ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при создании таблицы");
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        String sql = "drop table if exists user";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при удалении таблицы");
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        String sql = "insert into user(name, lastName, age) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении User в таблицу");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при сохранении User в таблицу");
                    e.printStackTrace();
                }
            }
        }

    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        String sql = "delete from user where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql) ) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении User по Id");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при удалении User по Id");
                    e.printStackTrace();
                }
            }
        }

    }

    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM USER";

        try(Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех User из таблицы");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при получении всех User из таблицы");
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        String sql = "delete from user";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы User");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка закрытия БД при очистке таблицы User");
                    e.printStackTrace();
                }
            }
        }
    }
}
