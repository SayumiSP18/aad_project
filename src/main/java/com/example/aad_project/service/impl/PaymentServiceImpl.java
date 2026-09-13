package com.example.aad_project.service.impl;

import com.example.aad_project.dto.PaymentDTO;
import com.example.aad_project.entity.Booking;
import com.example.aad_project.entity.Payment;
import com.example.aad_project.enumaration.PaymentStatus;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.BookingRepository;
import com.example.aad_project.repository.PaymentRepository;
import com.example.aad_project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public void savePayment(PaymentDTO paymentDTO) {
        try {
            if (paymentRepository.findByBooking_BookingId(paymentDTO.getBookingId()).isPresent())
                throw new CustomException(409, "Payment already recorded for this booking");

            Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                    .orElseThrow(() -> new CustomException(404, "Booking not found"));

            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
            payment.setStatus(PaymentStatus.PENDING);
            payment.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(payment);
            log.info("New payment recorded for booking: {}", booking.getBookingId());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save payment for booking {}: {}", paymentDTO.getBookingId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to record payment");
        }
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        try {
            return paymentRepository.getAllPayments();
        } catch (Exception e) {
            log.error("Failed to fetch payments: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch payments");
        }
    }

    @Override
    public List<PaymentDTO> filterPayments(PaymentStatus status) {
        try {
            return paymentRepository.filterPayments(status);
        } catch (Exception e) {
            log.error("Failed to filter payments by status {}: {}", status, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter payments");
        }
    }

    @Override
    public PaymentDTO selectPayment(long paymentId) {
        try {
            return paymentRepository.selectPayment(paymentId)
                    .orElseThrow(() -> new CustomException(404, "Payment not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch payment {}: {}", paymentId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch payment");
        }
    }

    @Override
    public void updatePayment(PaymentDTO paymentDTO) {
        try {
            Payment payment = paymentRepository.findById(paymentDTO.getPaymentId())
                    .orElseThrow(() -> new CustomException(404, "Payment not found"));

            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());

            if (paymentDTO.getStatus() != null)
                payment.setStatus(paymentDTO.getStatus());

            paymentRepository.save(payment);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update payment {}: {}", paymentDTO.getPaymentId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update payment");
        }
    }

    @Override
    public void deletePayment(long paymentId) {
        try {
            if (!paymentRepository.existsById(paymentId))
                throw new CustomException(404, "Payment not found");
            paymentRepository.deleteById(paymentId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete payment {}: {}", paymentId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete payment");
        }
    }
}