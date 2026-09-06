package com.example.aad_project.service.impl;

import com.example.aad_project.dto.BookingDTO;
import com.example.aad_project.entity.Booking;
import com.example.aad_project.entity.Branch;
import com.example.aad_project.entity.Parcel;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.BookingRepository;
import com.example.aad_project.repository.BranchRepository;
import com.example.aad_project.repository.ParcelRepository;
import com.example.aad_project.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ParcelRepository parcelRepository;
    private final BranchRepository branchRepository;

    @Override
    public void saveBooking(BookingDTO bookingDTO) {
        if (bookingRepository.findByParcel_ParcelId(bookingDTO.getParcelId()).isPresent())
            throw new CustomException(409, "Parcel is already booked");

        Parcel parcel = parcelRepository.findById(bookingDTO.getParcelId())
                .orElseThrow(() -> new CustomException(404, "Parcel not found"));
        Branch branch = branchRepository.findById(bookingDTO.getPickupBranchId())
                .orElseThrow(() -> new CustomException(404, "Pickup branch not found"));

        Booking booking = new Booking();
        booking.setParcel(parcel);
        booking.setPickupBranch(branch);
        booking.setBookingDate(LocalDateTime.now());
        booking.setEstimatedCost(bookingDTO.getEstimatedCost());
        bookingRepository.save(booking);
        log.info("New booking created for parcel: {}", parcel.getTrackingNo());
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    @Override
    public List<BookingDTO> filterBookings(Long pickupBranchId) {
        return bookingRepository.filterBookings(pickupBranchId);
    }

    @Override
    public BookingDTO selectBooking(long bookingId) {
        return bookingRepository.selectBooking(bookingId)
                .orElseThrow(() -> new CustomException(404, "Booking not found"));
    }

    @Override
    public void updateBooking(BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingDTO.getBookingId())
                .orElseThrow(() -> new CustomException(404, "Booking not found"));

        if (bookingDTO.getPickupBranchId() != null) {
            Branch branch = branchRepository.findById(bookingDTO.getPickupBranchId())
                    .orElseThrow(() -> new CustomException(404, "Pickup branch not found"));
            booking.setPickupBranch(branch);
        }

        booking.setEstimatedCost(bookingDTO.getEstimatedCost());
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(long bookingId) {
        if (!bookingRepository.existsById(bookingId))
            throw new CustomException(404, "Booking not found");
        bookingRepository.deleteById(bookingId);
    }
}
