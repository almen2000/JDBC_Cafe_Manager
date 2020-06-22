package com.cafe.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Connect {
    private static Connection connection = null;

    private Connect() {
    }

    public static Connection getMainConnection() {
        if (Objects.isNull(connection)) {
            try {
                //    final String url = "jdbc:postgresql://localhost/dvdrental";
                final String url = "jdbc:postgresql://localhost/cafe_manager";
                final String user = "postgres";
                final String password = "0000";
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to the PostgreSQL server successfully.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    public static Connection getConnection(final String url, final String user, final String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to another PostgreSQL server successfully.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
