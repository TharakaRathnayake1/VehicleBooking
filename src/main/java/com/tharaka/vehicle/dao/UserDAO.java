package com.tharaka.vehicle.dao;

import com.tharaka.vehicle.model.User;
import com.tharaka.vehicle.util.PasswordUtil;
import java.sql.*;
import java.security.NoSuchAlgorithmException;

public class UserDAO {
    public static int registerUser(User user) {
        int generatedId = -1;
        try (Connection conn = DBConnection.getConnection()) {
            // Generate a salt and hash the password using SHA-256 with the salt
            byte[] salt = PasswordUtil.getSalt();
            String hashedPassword = PasswordUtil.getSecurePassword(user.getPassword(), salt);
            String saltHex = PasswordUtil.bytesToHex(salt);
            
            // Updated SQL to store username, hashed password, salt, and role.
            String sql = "INSERT INTO users (username, password, salt, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, saltHex);
            stmt.setString(4, user.getRole());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
}
