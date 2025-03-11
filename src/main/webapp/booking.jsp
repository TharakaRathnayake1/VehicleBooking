<%@ page language="java" import="java.util.List, com.tharaka.vehicle.model.Car, com.tharaka.vehicle.model.Customer" pageEncoding="UTF-8"%>
<%
    // Use the implicit session object
    if(session == null || session.getAttribute("customerId") == null){
        response.sendRedirect("login.jsp?error=Please login as a customer");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book a Cab</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background: #f8f9fa;
            padding-top: 30px;
        }
        .booking-container {
            background: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: auto;
        }
        .booking-title {
            margin-bottom: 20px;
        }
        .form-label {
            font-weight: bold;
        }
        .back-link {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container booking-container">
        <h2 class="booking-title text-center text-primary">Book a Cab</h2>
        <form action="BookingServlet" method="post">
            <!-- The customer ID is taken from the session, so no customer dropdown -->

            <div class="form-group">
                <label for="vehicleId" class="form-label">Select Vehicle:</label>
                <select name="vehicleId" id="vehicleId" class="form-control" required>
                    <% 
                        List<Car> vehicles = (List<Car>) request.getAttribute("vehiclesList");
                        if(vehicles != null && !vehicles.isEmpty()) {
                            for(Car car : vehicles) {
                    %>
                        <option value="<%= car.getId() %>">
                            <%= car.getCarNumber() %> - <%= car.getModel() %>
                        </option>
                    <%      }
                        } else { 
                    %>
                        <option value="">No vehicles available</option>
                    <% } %>
                </select>
            </div>
            
            <!-- New: Driver selection dropdown -->
            <div class="form-group">
                <label for="driverId" class="form-label">Select Driver:</label>
                <select name="driverId" id="driverId" class="form-control" required>
                    <% 
                        List<Customer> drivers = (List<Customer>) request.getAttribute("driversList");
                        if(drivers != null && !drivers.isEmpty()) {
                            for(Customer driver : drivers) {
                    %>
                        <option value="<%= driver.getId() %>">
                            <%= driver.getName() %> - <%= driver.getCustomerType() %>
                        </option>
                    <%      }
                        } else { 
                    %>
                        <option value="">No drivers available</option>
                    <% } %>
                </select>
            </div>
            
            <div class="form-group">
                <label for="pickupLocation" class="form-label">Pickup Location:</label>
                <input type="text" name="pickupLocation" id="pickupLocation" class="form-control" placeholder="Enter Pickup Location" required>
            </div>
            
            <div class="form-group">
                <label for="destination" class="form-label">Destination:</label>
                <input type="text" name="destination" id="destination" class="form-control" placeholder="Enter Destination" required>
            </div>
            
            <div class="form-group">
                <label for="fare" class="form-label">Fare:</label>
                <input type="number" name="fare" id="fare" class="form-control" placeholder="Enter Fare" step="0.01" required>
            </div>
            
            <div class="text-center">
                <button type="submit" class="btn btn-primary btn-lg">Book Now</button>
            </div>
        </form>
        
        <div class="text-center back-link">
            <a href="customerDashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
        </div>
    </div>
    <!-- Optional Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
