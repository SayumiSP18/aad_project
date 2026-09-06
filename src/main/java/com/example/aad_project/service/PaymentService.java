package com.example.aad_project.service;

import com.example.aad_project.dto.PaymentDTO;
import com.example.aad_project.enumaration.PaymentStatus;

import java.util.List;

public interface PaymentService {
    void savePayment(PaymentDTO paymentDTO);

    List<PaymentDTO> getAllPayments();

    List<PaymentDTO> filterPayments(PaymentStatus status);

    PaymentDTO selectPayment(long paymentId);

    void updatePayment(PaymentDTO paymentDTO);

    void deletePayment(long paymentId);
}
