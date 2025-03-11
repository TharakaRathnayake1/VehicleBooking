<%@ page language="java" import="java.util.List, com.tharaka.vehicle.model.Booking" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Current Bookings</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background: #f8f9fa;
            padding-top: 30px;
        }
        .container {
            max-width: 1000px;
        }
        .table-container {
            background: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .back-link {
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container table-container">
        <h2 class="text-primary">Current Bookings</h2>
        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>Booking Number</th>
                        <th>Customer Name</th>
                        <th>Pickup Location</th>
                        <th>Destination</th>
                        <th>Vehicle Model</th>
                        <th>Fare</th>
                        <th>Booking Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Expecting each Booking object to have getCustomerName() and getVehicleModel() methods.
                        List<Booking> bookings = (List<Booking>) request.getAttribute("customerBookingsList");
                        if (bookings != null && !bookings.isEmpty()) {
                            for (Booking booking : bookings) {
                    %>
                    <tr>
                        <td><%= booking.getBookingNumber() %></td>
                        <td><%= booking.getCustomerName() %></td>
                        <td><%= booking.getPickupLocation() %></td>
                        <td><%= booking.getDestination() %></td>
                        <td><%= booking.getVehicleModel() %></td>
                        <td><%= booking.getFare() %></td>
                        <td><%= booking.getBookingDate() %></td>
                        <td>
                            <%
                                // Get the logged-in user's customerType from the implicit session object.
                                String customerType = (session != null && session.getAttribute("customerType") != null)
                                              ? (String) session.getAttribute("customerType")
                                              : "unknown";
                                if ("driver".equalsIgnoreCase(customerType)) {
                                    if ("Active".equalsIgnoreCase(booking.getStatus())) {
                            %>
                                        <form action="ConfirmBookingServlet" method="post" style="display:inline;">
                                            <input type="hidden" name="bookingId" value="<%= booking.getId() %>" />
                                            <button type="submit" class="btn btn-success btn-sm">Confirm</button>
                                        </form>
                                        <form action="CancelBookingServlet" method="post" style="display:inline;">
                                            <input type="hidden" name="bookingId" value="<%= booking.getId() %>" />
                                            <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                                        </form>
                            <%
                                    } else if ("Confirmed".equalsIgnoreCase(booking.getStatus())) {
                            %>
                                        <span class="btn btn-success btn-sm">Confirmed</span>
                            <%
                                    } else if ("Cancelled".equalsIgnoreCase(booking.getStatus())) {
                            %>
                                        <span class="text-muted">Cancelled</span>
                            <%
                                    }
                                } else { // Passenger side
                                    if ("Confirmed".equalsIgnoreCase(booking.getStatus())) {
                            %>
                                        <span class="btn btn-success btn-sm">Confirmed</span>
                            <%
                                    } else if (!"Cancelled".equalsIgnoreCase(booking.getStatus())) {
                            %>
                                        <form action="CancelBookingServlet" method="post" style="display:inline;">
                                            <input type="hidden" name="bookingId" value="<%= booking.getId() %>" />
                                            <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                                        </form>
                            <%
                                    } else {
                            %>
                                        <span class="text-muted">N/A</span>
                            <%
                                    }
                                }
                            %>
                        </td>                        
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="8" class="text-center">No bookings available.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        <div class="back-link">
            <a href="customerDashboard.jsp" class="btn btn-secondary btn-lg">Back to Dashboard</a>
        </div>
    </div>
    <!-- Optional Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
