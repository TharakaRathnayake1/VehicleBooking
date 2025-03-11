package com.tharaka.vehicle.dao;

import com.tharaka.vehicle.model.Bill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    // Inserts a new bill record into the database
    public static boolean addBill(Bill bill) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO bills (booking_id, total_amount, tax, discount, final_amount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bill.getBookingId());
            stmt.setDouble(2, bill.getTotalAmount());
            stmt.setDouble(3, bill.getTax());
            stmt.setDouble(4, bill.getDiscount());
            stmt.setDouble(5, bill.getFinalAmount());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieves a bill by booking ID
    public static Bill getBillByBookingId(int bookingId) {
        Bill bill = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, booking_id, total_amount, tax, discount, final_amount FROM bills WHERE booking_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBookingId(rs.getInt("booking_id"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setTax(rs.getDouble("tax"));
                bill.setDiscount(rs.getDouble("discount"));
                bill.setFinalAmount(rs.getDouble("final_amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }
}
