package com.example.aad_project.service;

import com.example.aad_project.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    void saveBooking(BookingDTO bookingDTO);

    List<BookingDTO> getAllBookings();

    List<BookingDTO> filterBookings(Long pickupBranchId);

    BookingDTO selectBooking(long bookingId);

    void updateBooking(BookingDTO bookingDTO);

    void deleteBooking(long bookingId);
}
