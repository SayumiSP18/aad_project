package com.example.aad_project.service.impl;

import com.example.aad_project.dto.InvoiceDTO;
import com.example.aad_project.entity.Invoice;
import com.example.aad_project.entity.Payment;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.InvoiceRepository;
import com.example.aad_project.repository.PaymentRepository;
import com.example.aad_project.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public void saveInvoice(InvoiceDTO invoiceDTO) {
        try {
            if (invoiceRepository.findByPayment_PaymentId(invoiceDTO.getPaymentId()).isPresent())
                throw new CustomException(409, "Invoice already generated for this payment");

            Payment payment = paymentRepository.findById(invoiceDTO.getPaymentId())
                    .orElseThrow(() -> new CustomException(404, "Payment not found"));

            Invoice invoice = new Invoice();
            invoice.setPayment(payment);
            invoice.setInvoiceNo("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            invoice.setIssuedDate(LocalDate.now());
            invoiceRepository.save(invoice);
            log.info("New invoice generated: {}", invoice.getInvoiceNo());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save invoice for payment {}: {}", invoiceDTO.getPaymentId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to generate invoice");
        }
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        try {
            return invoiceRepository.getAllInvoices();
        } catch (Exception e) {
            log.error("Failed to fetch invoices: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch invoices");
        }
    }

    @Override
    public List<InvoiceDTO> filterInvoices(String invoiceNo) {
        try {
            return invoiceRepository.filterInvoices(invoiceNo);
        } catch (Exception e) {
            log.error("Failed to filter invoices by invoice no '{}': {}", invoiceNo, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter invoices");
        }
    }

    @Override
    public InvoiceDTO selectInvoice(long invoiceId) {
        try {
            return invoiceRepository.selectInvoice(invoiceId)
                    .orElseThrow(() -> new CustomException(404, "Invoice not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch invoice {}: {}", invoiceId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch invoice");
        }
    }

    @Override
    public void updateInvoice(InvoiceDTO invoiceDTO) {
        try {
            Invoice invoice = invoiceRepository.findById(invoiceDTO.getInvoiceId())
                    .orElseThrow(() -> new CustomException(404, "Invoice not found"));

            if (invoiceDTO.getIssuedDate() != null)
                invoice.setIssuedDate(invoiceDTO.getIssuedDate());

            invoiceRepository.save(invoice);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update invoice {}: {}", invoiceDTO.getInvoiceId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update invoice");
        }
    }

    @Override
    public void deleteInvoice(long invoiceId) {
        try {
            if (!invoiceRepository.existsById(invoiceId))
                throw new CustomException(404, "Invoice not found");
            invoiceRepository.deleteById(invoiceId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete invoice {}: {}", invoiceId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete invoice");
        }
    }
}