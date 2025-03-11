<%@ page language="java" import="java.util.List,com.tharaka.vehicle.model.Booking" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Current Bookings</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background: url('https://img.freepik.com/free-vector/taxi-app-concept_23-2148485646.jpg?t=st=1741016319~exp=1741019919~hmac=ee22f30cfc4595de7a01c9f36335cbcc7c4ffa4b946c2ca0a72401207c4c72ce&w=740') no-repeat center center fixed;
            background-size: cover;
            padding-top: 30px;
            /* Optional fallback background color */
        }
        .container {
            max-width: 1100px;
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
                        List<Booking> bookings = (List<Booking>) request.getAttribute("adminBookingsList");
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
                            <a href="BillingServlet?bookingId=<%= booking.getId() %>" class="btn btn-primary btn-sm">View Billing</a>
                          </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center">No bookings available.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        <div class="back-link">
            <a href="adminDashboard.jsp" class="btn btn-secondary btn-lg">Back to Dashboard</a>
        </div>
    </div>
    <!-- Optional Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
