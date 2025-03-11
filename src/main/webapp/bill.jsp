<%@ page language="java" import="com.tharaka.vehicle.model.Bill" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Billing Details</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background: #f8f9fa;
            padding-top: 30px;
        }
        .billing-container {
            background: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: auto;
        }
        .billing-title {
            margin-bottom: 20px;
        }
        .billing-details p {
            font-size: 1.1rem;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container billing-container">
        <h2 class="billing-title text-center text-primary">Billing Details</h2>
        <div class="billing-details">
            <%
                Bill bill = (Bill) request.getAttribute("bill");
                if(bill != null) {
            %>
            <p><strong>Booking ID:</strong> <%= bill.getBookingId() %></p>
            <p><strong>Total Amount:</strong> <%= bill.getTotalAmount() %></p>
            <p><strong>Tax (10%):</strong> <%= bill.getTax() %></p>
            <p><strong>Discount:</strong> <%= bill.getDiscount() %></p>
            <p><strong>Final Amount:</strong> <%= bill.getFinalAmount() %></p>
            <%
                } else {
            %>
            <p class="text-danger">No billing details available.</p>
            <%
                }
            %>
        </div>
        <div class="text-center mt-4">
            <a href="adminDashboard.jsp" class="btn btn-secondary btn-lg">Back to Dashboard</a>
        </div>
    </div>
    <!-- Optional Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
