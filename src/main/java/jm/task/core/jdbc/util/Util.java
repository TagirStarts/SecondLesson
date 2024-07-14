package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
        private static final String URL = "jdbc:mysql://localhost:3306/lesson1";
        private static final String USER = "root";
        private static final String PASSWORD = "justfarm22";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            // JDBC Database connection settings
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/lesson1");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "justfarm22");

            // SQL dialect
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

            // Echo all executed SQL to stdout
            configuration.setProperty("hibernate.show_sql", "true");

            // Drop and re-create the database schema on startup
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // Add annotated class
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }



}
