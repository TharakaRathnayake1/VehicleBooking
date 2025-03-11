package com.tharaka.vehicle.dao;

import com.tharaka.vehicle.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Existing method to get all customers
    public static List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, name, address, nic, telephone, customer_type, email FROM customers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setNic(rs.getString("nic"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setCustomerType(rs.getString("customer_type"));
                customer.setEmail(rs.getString("email")); // New field for email
                list.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // New method to retrieve a customer by id (to get their email)
    public static Customer getCustomerById(int customerId) {
        Customer customer = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, name, address, nic, telephone, customer_type, email FROM customers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setNic(rs.getString("nic"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setCustomerType(rs.getString("customer_type"));
                customer.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Existing getAllDrivers() method should also retrieve email
    public static List<Customer> getAllDrivers() {
        List<Customer> drivers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, name, address, nic, telephone, customer_type, email FROM customers WHERE customer_type = 'driver'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer driver = new Customer();
                driver.setId(rs.getInt("id"));
                driver.setName(rs.getString("name"));
                driver.setAddress(rs.getString("address"));
                driver.setNic(rs.getString("nic"));
                driver.setTelephone(rs.getString("telephone"));
                driver.setCustomerType(rs.getString("customer_type"));
                driver.setEmail(rs.getString("email"));
                drivers.add(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public static boolean registerCustomer(Customer customer) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO customers (id, registration_number, name, address, nic, telephone, email, customer_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getRegistrationNumber());
            stmt.setString(3, customer.getName());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getNic());
            stmt.setString(6, customer.getTelephone());
            stmt.setString(7, customer.getEmail());
            stmt.setString(8, customer.getCustomerType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Customer getCustomerByEmail(String email) {
        Customer customer = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, name, address, nic, telephone, customer_type, email FROM customers WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setNic(rs.getString("nic"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setCustomerType(rs.getString("customer_type"));
                customer.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
