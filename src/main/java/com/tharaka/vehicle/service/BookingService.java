package com.tharaka.vehicle.service;

import com.tharaka.vehicle.model.Booking;
import java.util.List;

public interface BookingService {
    void createBooking(Booking booking);
    Booking getBookingById(int id);
    List<Booking> getAllBookings();
    void updateBooking(Booking booking);
    void deleteBooking(int id);
}
