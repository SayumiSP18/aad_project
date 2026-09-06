package com.example.aad_project.repository;

import com.example.aad_project.dto.PaymentDTO;
import com.example.aad_project.entity.Payment;
import com.example.aad_project.enumaration.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByBooking_BookingId(long bookingId);

    @Query(value = "SELECT new com.example.aad_project.dto.PaymentDTO(p.paymentId, p.booking.bookingId, p.amount, " +
            "p.paymentMethod, p.status, p.paymentDate) FROM Payment p")
    List<PaymentDTO> getAllPayments();

    @Query(value = "SELECT new com.example.aad_project.dto.PaymentDTO(p.paymentId, p.booking.bookingId, p.amount, " +
            "p.paymentMethod, p.status, p.paymentDate) FROM Payment p WHERE p.paymentId = :paymentId")
    Optional<PaymentDTO> selectPayment(@Param("paymentId") long paymentId);

    @Query(value = "SELECT new com.example.aad_project.dto.PaymentDTO(p.paymentId, p.booking.bookingId, p.amount, " +
            "p.paymentMethod, p.status, p.paymentDate) FROM Payment p WHERE (:status IS NULL OR p.status = :status)")
    List<PaymentDTO> filterPayments(@Param("status") PaymentStatus status);
}
