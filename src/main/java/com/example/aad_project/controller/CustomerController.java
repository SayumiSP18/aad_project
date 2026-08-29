package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.CustomerDTO;
import com.example.aad_project.dto.CustomerRegisterDTO;
import com.example.aad_project.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "v1/customers")
@CrossOrigin
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse register( @RequestBody CustomerRegisterDTO registerDTO) {
        customerService.registerCustomer(registerDTO);
        return new CommonResponse(0, "Customer registered successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return new CommonResponse(0, customers, "Get all customers");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterCustomers(@RequestParam(value = "fullName", required = false) String fullName) {
        List<CustomerDTO> customers = customerService.filterCustomers(fullName);
        return new CommonResponse(0, customers, "Filter customers");
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getMyProfile(Principal principal) {
        CustomerDTO dto = customerService.getMyProfile(principal.getName());
        return new CommonResponse(0, dto, "My profile");
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectCustomer(@PathVariable long customerId) {
        CustomerDTO dto = customerService.selectCustomer(customerId);
        return new CommonResponse(0, dto, "Customer details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateCustomer( @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerDTO);
        return new CommonResponse(0, "Customer updated");
    }

    @DeleteMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteCustomer(@PathVariable long customerId) {
        customerService.deleteCustomer(customerId);
        return new CommonResponse(0, "Customer deleted");
    }}
