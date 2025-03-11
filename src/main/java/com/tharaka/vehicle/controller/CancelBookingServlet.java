package com.tharaka.vehicle.controller;

import com.tharaka.vehicle.dao.BookingDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingIdStr = request.getParameter("bookingId");
        if (bookingIdStr != null && !bookingIdStr.isEmpty()) {
            try {
                int bookingId = Integer.parseInt(bookingIdStr);
                boolean cancelled = BookingDAO.cancelBooking(bookingId);
                if (cancelled) {
                    response.sendRedirect("customerDashboard.jsp?success=Booking cancelled successfully");
                } else {
                    response.sendRedirect("customerDashboard.jsp?error=Booking cancellation failed");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("customerDashboard.jsp?error=Invalid booking id format");
            }
        } else {
            response.sendRedirect("customerDashboard.jsp?error=Booking id not provided");
        }
    }
}
