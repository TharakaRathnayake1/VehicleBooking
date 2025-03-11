package com.tharaka.vehicle.controller;

import com.tharaka.vehicle.dao.BillingDAO;
import com.tharaka.vehicle.dao.BookingDAO;
import com.tharaka.vehicle.model.Bill;
import com.tharaka.vehicle.model.Booking;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BillingServlet")
public class BillingServlet extends HttpServlet {
    
    // Optionally, you may remove doPost if billing is now always generated from admin dashboard via GET.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // For backward compatibility, you can leave this method or redirect to GET.
        doGet(request, response);
    }

    // Processes GET requests to display a bill for a given bookingId.
    // If no bill exists, compute billing from booking.fare, create the bill record, then display.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingIdStr = request.getParameter("bookingId");
        if (bookingIdStr != null && !bookingIdStr.isEmpty()) {
            try {
                int bookingId = Integer.parseInt(bookingIdStr);
                Bill bill = BillingDAO.getBillByBookingId(bookingId);
                if (bill == null) {
                    // Bill does not exist, so retrieve the booking details and compute bill
                    Booking booking = BookingDAO.getBookingById(bookingId);
                    if (booking != null) {
                        double totalAmount = booking.getFare();  // Using booking fare as total amount
                        double tax = totalAmount * 0.10;          // 10% tax
                        double discount = totalAmount > 1000 ? 50 : 0;  // Fixed discount if applicable
                        double finalAmount = totalAmount + tax - discount;
                        
                        bill = new Bill();
                        bill.setBookingId(bookingId);
                        bill.setTotalAmount(totalAmount);
                        bill.setTax(tax);
                        bill.setDiscount(discount);
                        bill.setFinalAmount(finalAmount);
                        
                        boolean success = BillingDAO.addBill(bill);
                        if (!success) {
                            response.sendRedirect("adminDashboard.jsp?error=Bill creation failed");
                            return;
                        }
                    } else {
                        response.sendRedirect("adminDashboard.jsp?error=Booking not found");
                        return;
                    }
                }
                // Forward to bill.jsp to display the bill
                request.setAttribute("bill", bill);
                request.getRequestDispatcher("bill.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("adminDashboard.jsp?error=Invalid booking id format");
            }
        } else {
            response.sendRedirect("adminDashboard.jsp?error=Booking id not provided");
        }
    }
}
