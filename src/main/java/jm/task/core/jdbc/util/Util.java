package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/jmbase";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";


    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static SessionFactory concreteSessionFactory = null;

    static {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jmbase");
            prop.setProperty("hibernate.connection.username", "root");
            prop.setProperty("hibernate.connection.password", "12345678");
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            prop.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            prop.setProperty("hibernate.connection.autocommit", "false");

            concreteSessionFactory = new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

        } catch (ExceptionInInitializerError ex) {
            System.err.println("Ошибка при инициализации SesstionFactory");
            ex.printStackTrace();
        }
    }

    public static Session getSession() throws HibernateException {
        return concreteSessionFactory.openSession();
    }
}
