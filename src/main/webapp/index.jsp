<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cab Booking</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <style>
        .navbar { background-color: #007bff; }
        .navbar-brand, .nav-link { color: white !important; }

        /* Notice Styling */
        .notice {
            margin-top: 20px;
            font-size: 18px;
            color: red;
            font-weight: bold;
            text-align: center;
            border: 2px solid red;
            padding: 10px;
            background-color: #ffecec;
            border-radius: 8px;
        }

        .carousel-inner img { width: 100%; height: 500px; object-fit: cover; }
    </style>
</head>
<body>

    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="#">Mega City Cab</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="login.jsp">Login</a></li>
                    <li class="nav-item"><a class="nav-link" href="register.jsp">Register</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container text-center mt-4">
        <h2>Welcome to the Cab Booking System</h2>
        <p>Book a cab and enjoy a comfortable ride.</p>

        <!-- Notice -->
        <p class="notice">🚖 Rs. 50 per km 🚖</p>
    </div>

    <!-- Car Slideshow -->
    <div id="carCarousel" class="carousel slide mt-4" data-bs-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="images/car1.jpg" class="d-block w-100" alt="Car 1">
            </div>
            <div class="carousel-item">
                <img src="images/car2.jpg" class="d-block w-100" alt="Car 2">
            </div>
            <div class="carousel-item">
                <img src="images/car3.jpg" class="d-block w-100" alt="Car 3">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
        </button>
    </div>

    <!-- Bootstrap Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
