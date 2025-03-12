package com.tharaka.vehicle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   private static final String URL = "jdbc:mysql://localhost:3306/cab_booking";
   private static final String USER = "root";
   private static final String PASSWORD = "Tharuicbt01";

   private static Connection connection; // Single instance of Connection

   // Private constructor to prevent instantiation
   private DBConnection() {}

   // Factory method to get a single connection instance
   public static synchronized Connection getConnection() {
       if (connection == null) {
           try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               connection = DriverManager.getConnection(URL, USER, PASSWORD);
           } catch (ClassNotFoundException | SQLException e) {
               throw new RuntimeException("Error connecting to the database", e);
           }
       }
       return connection;
   }
}