<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Registration</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background: #f8f9fa;
            padding-top: 30px;
        }
        .registration-container {
            background: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: auto;
        }
        .registration-title {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container registration-container">
        <h2 class="registration-title text-center text-primary">Customer Registration</h2>
        <form action="RegisterServlet" method="post">
            <!-- User credentials -->
            <div class="form-group">
                <label for="username" class="form-label">Username:</label>
                <input type="text" name="username" id="username" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="password" class="form-label">Password:</label>
                <input type="password" name="password" id="password" class="form-control" required>
            </div>
            <!-- Customer details -->
            <div class="form-group">
                <label for="name" class="form-label">Full Name:</label>
                <input type="text" name="name" id="name" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="address" class="form-label">Address:</label>
                <input type="text" name="address" id="address" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="nic" class="form-label">NIC:</label>
                <input type="text" name="nic" id="nic" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="telephone" class="form-label">Telephone:</label>
                <input type="text" name="telephone" id="telephone" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="email" class="form-label">Email:</label>
                <input type="email" name="email" id="email" class="form-control" required>
            </div>
            <!-- Customer type: Default to Passenger; can be changed to Driver if needed -->
            <div class="form-group">
                <label for="customerType" class="form-label">Customer Type:</label>
                <select name="customerType" id="customerType" class="form-control" required>
                    <option value="passenger" selected>Passenger</option>
                    <option value="driver">Driver</option>
                </select>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary btn-lg">Register</button>
            </div>
        </form>
        <div class="text-center mt-3">
            <a href="login.jsp" class="btn btn-secondary">Back to Login</a>
        </div>
    </div>
    <!-- Optional Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
