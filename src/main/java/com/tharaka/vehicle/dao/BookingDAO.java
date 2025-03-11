package com.tharaka.vehicle.dao;

import com.tharaka.vehicle.model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Inserts a new booking record into the database including driver_id, pickup location, and status
    public static boolean addBooking(Booking booking) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO bookings (booking_number, customer_id, vehicle_id, driver_id, destination, pickup_location, fare, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, booking.getBookingNumber());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setInt(3, booking.getVehicleId());
            stmt.setInt(4, booking.getDriverId());
            stmt.setString(5, booking.getDestination());
            stmt.setString(6, booking.getPickupLocation());
            stmt.setDouble(7, booking.getFare());
            stmt.setString(8, booking.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieves all bookings (with JOINs)
    public static List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT b.id, b.booking_number, b.destination, b.fare, b.booking_date, b.pickup_location, b.status, b.driver_id, " +
                         "c.name AS customer_name, car.model AS vehicle_model " +
                         "FROM bookings b " +
                         "JOIN customers c ON b.customer_id = c.id " +
                         "JOIN cars car ON b.vehicle_id = car.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setBookingNumber(rs.getString("booking_number"));
                booking.setDestination(rs.getString("destination"));
                booking.setFare(rs.getDouble("fare"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setPickupLocation(rs.getString("pickup_location"));
                booking.setStatus(rs.getString("status"));
                booking.setDriverId(rs.getInt("driver_id"));
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setVehicleModel(rs.getString("vehicle_model"));
                list.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Retrieves a single booking by its ID
    public static Booking getBookingById(int bookingId) {
        Booking booking = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM bookings WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setBookingNumber(rs.getString("booking_number"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setDriverId(rs.getInt("driver_id"));
                booking.setDestination(rs.getString("destination"));
                booking.setPickupLocation(rs.getString("pickup_location"));
                booking.setFare(rs.getDouble("fare"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }
    
    // Cancels a booking by updating its status to "Cancelled"
    public static boolean cancelBooking(int bookingId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE bookings SET status = 'Cancelled' WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieves bookings for a specific customer
    public static List<Booking> getBookingsByCustomerId(int customerId) {
        List<Booking> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT b.id, b.booking_number, b.destination, b.fare, b.booking_date, b.pickup_location, b.status, b.driver_id, " +
                         "c.name AS customer_name, car.model AS vehicle_model " +
                         "FROM bookings b " +
                         "JOIN customers c ON b.customer_id = c.id " +
                         "JOIN cars car ON b.vehicle_id = car.id " +
                         "WHERE b.customer_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setBookingNumber(rs.getString("booking_number"));
                booking.setDestination(rs.getString("destination"));
                booking.setFare(rs.getDouble("fare"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setPickupLocation(rs.getString("pickup_location"));
                booking.setStatus(rs.getString("status"));
                booking.setDriverId(rs.getInt("driver_id"));
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setVehicleModel(rs.getString("vehicle_model"));
                list.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // New method: Update all bookings that share the same base booking number to "Confirmed"
    public static boolean confirmBookingByBaseId(String baseId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE bookings SET status = 'Confirmed' WHERE booking_number LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, baseId + "%");
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
