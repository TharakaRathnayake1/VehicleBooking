package com.tharaka.vehicle.controller;

import com.tharaka.vehicle.dao.DBConnection;
import com.tharaka.vehicle.util.PasswordUtil;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    // Helper method: convert hex string to byte array
    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for(int i = 0; i < len; i += 2){
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String providedPassword = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            // Retrieve stored user data, including hashed password and salt
            String sql = "SELECT u.id, u.role, u.password as storedPassword, u.salt, c.customer_type " +
                         "FROM users u " +
                         "LEFT JOIN customers c ON u.id = c.id " +
                         "WHERE u.username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String role = rs.getString("role");
                String storedPassword = rs.getString("storedPassword");
                String saltHex = rs.getString("salt");
                String customerType = rs.getString("customer_type");

                // Convert salt from hex string to byte array
                byte[] salt = hexStringToByteArray(saltHex);
                // Hash the provided password with the retrieved salt using SHA-256
                String hashedPassword = PasswordUtil.getSecurePassword(providedPassword, salt);

                if (storedPassword.equals(hashedPassword)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", username);
                    session.setAttribute("role", role);
                    if ("customer".equalsIgnoreCase(role)) {
                        session.setAttribute("customerId", userId);
                        session.setAttribute("customerType", customerType != null ? customerType : "passenger");
                        response.sendRedirect("customerDashboard.jsp");
                    } else if ("admin".equalsIgnoreCase(role)) {
                        response.sendRedirect("adminDashboard.jsp");
                    } else if ("manager".equalsIgnoreCase(role)) {
                        response.sendRedirect("managerDashboard.jsp");
                    } else {
                        response.sendRedirect("dashboard.jsp");
                    }
                } else {
                    response.sendRedirect("login.jsp?error=Invalid Credentials");
                }
            } else {
                response.sendRedirect("login.jsp?error=Invalid Credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Internal Server Error");
        }
    }
    
    // Redirect GET requests to the login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
