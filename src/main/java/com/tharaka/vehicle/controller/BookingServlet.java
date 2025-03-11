package com.tharaka.vehicle.controller;

import com.tharaka.vehicle.dao.BookingDAO;
import com.tharaka.vehicle.dao.CustomerDAO;
import com.tharaka.vehicle.dao.CarDAO;
import com.tharaka.vehicle.model.Booking;
import com.tharaka.vehicle.model.Customer;
import com.tharaka.vehicle.util.EmailUtil;
import com.tharaka.vehicle.model.Car;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {

    // Handle POST requests for booking submissions
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the passenger's (customer's) ID from the session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            response.sendRedirect("login.jsp?error=Please login as a customer");
            return;
        }
        int passengerId = (Integer) session.getAttribute("customerId");

        // Retrieve form parameters
        String vehicleIdStr = request.getParameter("vehicleId");
        int vehicleId = Integer.parseInt(vehicleIdStr);

        // Retrieve the driver id from the form (driver select dropdown)
        String driverIdStr = request.getParameter("driverId");
        int driverId = Integer.parseInt(driverIdStr);

        String destination = request.getParameter("destination");
        String pickupLocation = request.getParameter("pickupLocation");
        double fare = Double.parseDouble(request.getParameter("fare"));

        // Generate a common base ID for correlation using UUID
        String baseId = UUID.randomUUID().toString();
        String passengerBookingNumber = "BOOK" + baseId + "-P";
        String driverBookingNumber = "BOOK" + baseId + "-D";

        // Create booking record for the passenger
        Booking passengerBooking = new Booking();
        passengerBooking.setCustomerId(passengerId);
        passengerBooking.setVehicleId(vehicleId);
        passengerBooking.setDriverId(driverId); // New field for selected driver
        passengerBooking.setDestination(destination);
        passengerBooking.setPickupLocation(pickupLocation);
        passengerBooking.setFare(fare);
        passengerBooking.setBookingNumber(passengerBookingNumber);
        passengerBooking.setStatus("Active");

        // Create booking record for the driver using the driver's customer id
        Booking driverBooking = new Booking();
        driverBooking.setCustomerId(driverId); // driver's customer id
        driverBooking.setVehicleId(vehicleId);
        driverBooking.setDriverId(driverId);
        driverBooking.setDestination(destination);
        driverBooking.setPickupLocation(pickupLocation);
        driverBooking.setFare(fare);
        driverBooking.setBookingNumber(driverBookingNumber);
        driverBooking.setStatus("Active");

        // Insert both booking records
        boolean successPassenger = BookingDAO.addBooking(passengerBooking);
        boolean successDriver = BookingDAO.addBooking(driverBooking);

        if (successPassenger && successDriver) {
            // Retrieve email addresses using CustomerDAO
            String passengerEmail = "";
            String driverEmail = "";
            Customer passenger = CustomerDAO.getCustomerById(passengerId);
            if (passenger != null) {
                passengerEmail = passenger.getEmail();
            }
            Customer driver = CustomerDAO.getCustomerById(driverId);
            if (driver != null) {
                driverEmail = driver.getEmail();
            }

            String subject = "Booking Confirmation";
            String messageForPassenger = "Your booking with number " + passengerBookingNumber + " has been confirmed.";
            String messageForDriver = "A new booking (" + driverBookingNumber + ") has been assigned to you.";

            // SMTP server configuration for Gmail
            String host = "smtp.gmail.com";
            String port = "587";
            String smtpUser = "tharakabandararath@gmail.com"; // Replace with your Gmail address
            String smtpPass = "omsm cnoc sgxt bdqo"; // Replace with your App Password

            try {
                // Send email to passenger
                EmailUtil.sendEmail(host, port, smtpUser, smtpPass, passengerEmail, subject, messageForPassenger);
                // Send email to driver
                EmailUtil.sendEmail(host, port, smtpUser, smtpPass, driverEmail, subject, messageForDriver);
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
            response.sendRedirect("customerDashboard.jsp?success=Booking Added");
        } else {
            response.sendRedirect("booking.jsp?error=Booking Failed");
        }
    }

    // Handle GET requests:
    // If "action=list", load and forward to a JSP that displays all bookings.
    // Else if "adminlist", load and forward to a JSP that displays admin bookings.
    // Else if "customerlist", retrieve the logged-in customer's bookings.
    // Otherwise, load customer, vehicle, and driver lists and forward to the
    // booking form.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("list".equalsIgnoreCase(action)) {
            List<Booking> bookings = BookingDAO.getAllBookings();
            request.setAttribute("bookingsList", bookings);
            request.getRequestDispatcher("bookingsList.jsp").forward(request, response);
        } else if ("adminlist".equalsIgnoreCase(action)) {
            List<Booking> bookings = BookingDAO.getAllBookings();
            request.setAttribute("adminBookingsList", bookings);
            request.getRequestDispatcher("adminBookingsList.jsp").forward(request, response);
        } else if ("customerlist".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("customerId") != null) {
                int customerId = (Integer) session.getAttribute("customerId");
                List<Booking> bookings = BookingDAO.getBookingsByCustomerId(customerId);
                request.setAttribute("customerBookingsList", bookings);
                request.getRequestDispatcher("customerBookingsList.jsp").forward(request, response);
            } else {
                response.sendRedirect("login.jsp?error=Please login as a customer");
            }
        } else {
            // Load lists for booking form
            List<Customer> customers = CustomerDAO.getAllCustomers();
            request.setAttribute("customersList", customers);
            List<Car> vehicles = CarDAO.getAllVehicles();
            request.setAttribute("vehiclesList", vehicles);
            // Load drivers from customers table (customer_type = 'driver')
            List<Customer> drivers = CustomerDAO.getAllDrivers();
            request.setAttribute("driversList", drivers);
            request.getRequestDispatcher("booking.jsp").forward(request, response);
        }
    }
}
