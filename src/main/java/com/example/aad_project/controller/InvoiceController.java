package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.InvoiceDTO;
import com.example.aad_project.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/invoices")
@CrossOrigin
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        invoiceService.saveInvoice(invoiceDTO);
        return new CommonResponse(0, "Invoice generated successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return new CommonResponse(0, invoices, "Get all invoices");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterInvoices(@RequestParam(value = "invoiceNo", required = false) String invoiceNo) {
        List<InvoiceDTO> invoices = invoiceService.filterInvoices(invoiceNo);
        return new CommonResponse(0, invoices, "Filter invoices");
    }

    @GetMapping(value = "/{invoiceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectInvoice(@PathVariable long invoiceId) {
        InvoiceDTO dto = invoiceService.selectInvoice(invoiceId);
        return new CommonResponse(0, dto, "Invoice details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        invoiceService.updateInvoice(invoiceDTO);
        return new CommonResponse(0, "Invoice updated");
    }

    @DeleteMapping(value = "/{invoiceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteInvoice(@PathVariable long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return new CommonResponse(0, "Invoice deleted");
    }
}
