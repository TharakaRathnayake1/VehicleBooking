package com.tharaka.vehicle.dao;

import com.tharaka.vehicle.model.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    // Retrieves a list of available vehicles
    public static List<Car> getAllVehicles() {
        List<Car> vehicles = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            // Select only available vehicles
            String sql = "SELECT id, car_number, model, availability FROM cars WHERE availability = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setCarNumber(rs.getString("car_number"));
                car.setModel(rs.getString("model"));
                car.setAvailability(rs.getBoolean("availability"));
                vehicles.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}
