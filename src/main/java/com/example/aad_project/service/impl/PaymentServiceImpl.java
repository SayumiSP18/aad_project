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
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    @Override
    public List<PaymentDTO> filterPayments(PaymentStatus status) {
        return paymentRepository.filterPayments(status);
    }

    @Override
    public PaymentDTO selectPayment(long paymentId) {
        return paymentRepository.selectPayment(paymentId)
                .orElseThrow(() -> new CustomException(404, "Payment not found"));
    }

    @Override
    public void updatePayment(PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(paymentDTO.getPaymentId())
                .orElseThrow(() -> new CustomException(404, "Payment not found"));

        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());

        if (paymentDTO.getStatus() != null)
            payment.setStatus(paymentDTO.getStatus());

        paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(long paymentId) {
        if (!paymentRepository.existsById(paymentId))
            throw new CustomException(404, "Payment not found");
        paymentRepository.deleteById(paymentId);
    }}
