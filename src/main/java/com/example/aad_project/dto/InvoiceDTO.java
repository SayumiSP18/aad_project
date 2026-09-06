package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private long invoiceId;

//    @NotNull(message = "Payment is required")
    private Long paymentId;

    private String invoiceNo;
    private LocalDate issuedDate;

    public InvoiceDTO(long invoiceId, long paymentId, String invoiceNo, LocalDate issuedDate) {
        this.invoiceId = invoiceId;
        this.paymentId = paymentId;
        this.invoiceNo = invoiceNo;
        this.issuedDate = issuedDate;
    }}
