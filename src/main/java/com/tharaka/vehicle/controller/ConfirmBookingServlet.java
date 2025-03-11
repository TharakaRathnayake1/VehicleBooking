package com.tharaka.vehicle.controller;

import com.tharaka.vehicle.dao.BookingDAO;
import com.tharaka.vehicle.model.Booking;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ConfirmBookingServlet")
public class ConfirmBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the booking id from the form (driver side)
        String bookingIdStr = request.getParameter("bookingId");
        if (bookingIdStr == null || bookingIdStr.isEmpty()) {
            response.sendRedirect("customerDashboard.jsp?error=Booking id not provided");
            return;
        }
        int bookingId = Integer.parseInt(bookingIdStr);

        // Retrieve the booking record (assumed to be the driver's booking)
        Booking driverBooking = BookingDAO.getBookingById(bookingId);
        if (driverBooking == null) {
            response.sendRedirect("customerDashboard.jsp?error=Booking not found");
            return;
        }
        
        // Extract the common base ID from the booking number.
        // Our booking number is generated as "BOOK" + baseId + "-P" or "-D".
        String bookingNumber = driverBooking.getBookingNumber();
        int dashIndex = bookingNumber.lastIndexOf("-");
        if (dashIndex == -1) {
            response.sendRedirect("customerDashboard.jsp?error=Invalid booking number format");
            return;
        }
        String baseId = bookingNumber.substring(0, dashIndex);
        
        // Update both booking records (passenger and driver) that share the baseId to "Confirmed"
        boolean updated = BookingDAO.confirmBookingByBaseId(baseId);
        if (updated) {
            response.sendRedirect("customerDashboard.jsp?success=Booking Confirmed");
        } else {
            response.sendRedirect("customerDashboard.jsp?error=Confirmation Failed");
        }
    }
}
