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
        try {
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
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save booking for parcel {}: {}", bookingDTO.getParcelId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to create booking");
        }
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        try {
            return bookingRepository.getAllBookings();
        } catch (Exception e) {
            log.error("Failed to fetch bookings: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch bookings");
        }
    }

    @Override
    public List<BookingDTO> filterBookings(Long pickupBranchId) {
        try {
            return bookingRepository.filterBookings(pickupBranchId);
        } catch (Exception e) {
            log.error("Failed to filter bookings by branch {}: {}", pickupBranchId, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter bookings");
        }
    }

    @Override
    public BookingDTO selectBooking(long bookingId) {
        try {
            return bookingRepository.selectBooking(bookingId)
                    .orElseThrow(() -> new CustomException(404, "Booking not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch booking {}: {}", bookingId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch booking");
        }
    }

    @Override
    public void updateBooking(BookingDTO bookingDTO) {
        try {
            Booking booking = bookingRepository.findById(bookingDTO.getBookingId())
                    .orElseThrow(() -> new CustomException(404, "Booking not found"));

            if (bookingDTO.getPickupBranchId() != null) {
                Branch branch = branchRepository.findById(bookingDTO.getPickupBranchId())
                        .orElseThrow(() -> new CustomException(404, "Pickup branch not found"));
                booking.setPickupBranch(branch);
            }

            booking.setEstimatedCost(bookingDTO.getEstimatedCost());
            bookingRepository.save(booking);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update booking {}: {}", bookingDTO.getBookingId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update booking");
        }
    }

    @Override
    public void deleteBooking(long bookingId) {
        try {
            if (!bookingRepository.existsById(bookingId))
                throw new CustomException(404, "Booking not found");
            bookingRepository.deleteById(bookingId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete booking {}: {}", bookingId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete booking");
        }
    }
}