package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.BookingDTO;
import com.example.aad_project.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/bookings")
@CrossOrigin
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveBooking( @RequestBody BookingDTO bookingDTO) {
        bookingService.saveBooking(bookingDTO);
        return new CommonResponse(0, "Booking created successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return new CommonResponse(0, bookings, "Get all bookings");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterBookings(@RequestParam(value = "pickupBranchId", required = false) Long pickupBranchId) {
        List<BookingDTO> bookings = bookingService.filterBookings(pickupBranchId);
        return new CommonResponse(0, bookings, "Filter bookings");
    }


}
