package com.example.aad_project.service;

import com.example.aad_project.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    void saveInvoice(InvoiceDTO invoiceDTO);

    List<InvoiceDTO> getAllInvoices();

    List<InvoiceDTO> filterInvoices(String invoiceNo);

    InvoiceDTO selectInvoice(long invoiceId);

    void updateInvoice(InvoiceDTO invoiceDTO);

    void deleteInvoice(long invoiceId);
}
