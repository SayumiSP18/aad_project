package com.example.aad_project.dto;

import com.example.aad_project.enumaration.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private long paymentId;

//    @NotNull(message = "Booking is required")
    private Long bookingId;

//    @Positive(message = "Amount must be greater than 0")
    private double amount;

//    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private PaymentStatus status;
    private LocalDateTime paymentDate;

    public PaymentDTO(long paymentId, long bookingId, double amount, String paymentMethod,
                      PaymentStatus status, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentDate = paymentDate;
    }
}
