package com.example.aad_project.repository;

import com.example.aad_project.dto.InvoiceDTO;
import com.example.aad_project.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByPayment_PaymentId(long paymentId);

    @Query(value = "SELECT new com.example.aad_project.dto.InvoiceDTO(i.invoiceId, i.payment.paymentId, i.invoiceNo, i.issuedDate) " +
            "FROM Invoice i")
    List<InvoiceDTO> getAllInvoices();

    @Query(value = "SELECT new com.example.aad_project.dto.InvoiceDTO(i.invoiceId, i.payment.paymentId, i.invoiceNo, i.issuedDate) " +
            "FROM Invoice i WHERE i.invoiceId = :invoiceId")
    Optional<InvoiceDTO> selectInvoice(@Param("invoiceId") long invoiceId);

    @Query(value = "SELECT new com.example.aad_project.dto.InvoiceDTO(i.invoiceId, i.payment.paymentId, i.invoiceNo, i.issuedDate) " +
            "FROM Invoice i WHERE (:invoiceNo IS NULL OR i.invoiceNo LIKE %:invoiceNo%)")
    List<InvoiceDTO> filterInvoices(@Param("invoiceNo") String invoiceNo);
}
