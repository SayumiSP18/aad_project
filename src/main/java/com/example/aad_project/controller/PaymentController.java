package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.PaymentDTO;
import com.example.aad_project.enumaration.PaymentStatus;
import com.example.aad_project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/payments")
@CrossOrigin
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse savePayment( @RequestBody PaymentDTO paymentDTO) {
        paymentService.savePayment(paymentDTO);
        return new CommonResponse(0, "Payment recorded successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return new CommonResponse(0, payments, "Get all payments");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterPayments(@RequestParam(value = "status", required = false) PaymentStatus status) {
        List<PaymentDTO> payments = paymentService.filterPayments(status);
        return new CommonResponse(0, payments, "Filter payments");
    }

    @GetMapping(value = "/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectPayment(@PathVariable long paymentId) {
        PaymentDTO dto = paymentService.selectPayment(paymentId);
        return new CommonResponse(0, dto, "Payment details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updatePayment( @RequestBody PaymentDTO paymentDTO) {
        paymentService.updatePayment(paymentDTO);
        return new CommonResponse(0, "Payment updated");
    }

    @DeleteMapping(value = "/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deletePayment(@PathVariable long paymentId) {
        paymentService.deletePayment(paymentId);
        return new CommonResponse(0, "Payment deleted");
    }
}
