package com.project.shopping.cart.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbCon {

    private static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/ecommerce_cart?characterEncoding=utf-8";
            connection = DriverManager.getConnection(dbURL, "root", "iloveaey");

            System.out.println("Connect");
        }

        return connection;
    }

}
