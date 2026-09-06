package com.example.aad_project.repository;

import com.example.aad_project.dto.BookingDTO;
import com.example.aad_project.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByParcel_ParcelId(long parcelId);

    @Query(value = "SELECT new com.example.aad_project.dto.BookingDTO(b.bookingId, b.parcel.parcelId, b.parcel.trackingNo, " +
            "b.pickupBranch.branchId, b.pickupBranch.name, b.bookingDate, b.estimatedCost) FROM Booking b")
    List<BookingDTO> getAllBookings();

    @Query(value = "SELECT new com.example.aad_project.dto.BookingDTO(b.bookingId, b.parcel.parcelId, b.parcel.trackingNo, " +
            "b.pickupBranch.branchId, b.pickupBranch.name, b.bookingDate, b.estimatedCost) FROM Booking b WHERE b.bookingId = :bookingId")
    Optional<BookingDTO> selectBooking(@Param("bookingId") long bookingId);

    @Query(value = "SELECT new com.example.aad_project.dto.BookingDTO(b.bookingId, b.parcel.parcelId, b.parcel.trackingNo, " +
            "b.pickupBranch.branchId, b.pickupBranch.name, b.bookingDate, b.estimatedCost) FROM Booking b " +
            "WHERE (:pickupBranchId IS NULL OR b.pickupBranch.branchId = :pickupBranchId)")
    List<BookingDTO> filterBookings(@Param("pickupBranchId") Long pickupBranchId);
}
