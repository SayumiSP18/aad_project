package com.example.aad_project.service;

import com.example.aad_project.dto.CustomerDTO;
import com.example.aad_project.dto.CustomerRegisterDTO;

import java.util.List;

public interface CustomerService {

    void registerCustomer(CustomerRegisterDTO registerDTO);

    List<CustomerDTO> getAllCustomers();

    List<CustomerDTO> filterCustomers(String fullName);

    CustomerDTO selectCustomer(long customerId);

    CustomerDTO getMyProfile(String username);

    void updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(long customerId);
}
