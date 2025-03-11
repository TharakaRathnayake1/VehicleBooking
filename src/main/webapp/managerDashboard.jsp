<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Manager Dashboard</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
    body {
            background: url('https://img.freepik.com/free-vector/taxi-app-concept_23-2148485646.jpg?t=st=1741016319~exp=1741019919~hmac=ee22f30cfc4595de7a01c9f36335cbcc7c4ffa4b946c2ca0a72401207c4c72ce&w=740') no-repeat center center fixed;
            background-size: cover;
            padding-top: 30px;
    }
    .dashboard-container {
      max-width: 600px;
      margin: auto;
      background: #ffffff;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
    .dashboard-header {
      margin-bottom: 30px;
    }
    .dashboard-header h2 {
      color: #007bff;
    }
    .dashboard-links a {
      margin: 10px;
    }
  </style>
</head>
<body>
  <div class="container dashboard-container text-center">
    <div class="dashboard-header">
      <h2>Welcome, Manager!</h2>
      <p class="lead">This is your manager dashboard.</p>
    </div>
    <div class="dashboard-links">
      <a href="BookingServlet?action=list" class="btn btn-info btn-lg">View Current Bookings</a>
      <a href="LogoutServlet" class="btn btn-danger btn-lg">Logout</a>
    </div>
  </div>
  
  <!-- Optional Bootstrap JS and dependencies -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
