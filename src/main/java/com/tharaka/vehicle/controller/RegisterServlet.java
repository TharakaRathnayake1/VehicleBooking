package com.tharaka.vehicle.controller;

import com.tharaka.vehicle.dao.UserDAO;
import com.tharaka.vehicle.dao.CustomerDAO;
import com.tharaka.vehicle.model.User;
import com.tharaka.vehicle.model.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve user credentials from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Retrieve customer details from the form
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String nic = request.getParameter("nic");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String customerType = request.getParameter("customerType");

        // Check if the email already exists in the customers table
        Customer existingCustomer = CustomerDAO.getCustomerByEmail(email);
        if(existingCustomer != null) {
            response.sendRedirect("register.jsp?error=Email already exists");
            return;
        }

        // Create a User object for the users table
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("customer");

        // Register the user and retrieve the generated user id (assumed to be the customer id)
        int userId = UserDAO.registerUser(user);
        if (userId > 0) {
            // Create a Customer object for the customers table
            Customer customer = new Customer();
            customer.setId(userId); // Assuming the customer's id matches the user's generated id
            // Generate a registration number (for example, "CUST" followed by a zero-padded number)
            customer.setRegistrationNumber(String.format("CUST%03d", userId));
            customer.setName(name);
            customer.setAddress(address);
            customer.setNic(nic);
            customer.setTelephone(telephone);
            customer.setEmail(email);
            customer.setCustomerType(customerType);

            boolean success = CustomerDAO.registerCustomer(customer);
            if (success) {
                response.sendRedirect("login.jsp?success=Registration Successful, Please login");
            } else {
                response.sendRedirect("register.jsp?error=Registration failed (customer data)");
            }
        } else {
            response.sendRedirect("register.jsp?error=Registration failed (user data)");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}
