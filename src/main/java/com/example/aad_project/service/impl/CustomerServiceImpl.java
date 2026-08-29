package com.example.aad_project.service.impl;

import com.example.aad_project.dto.CustomerDTO;
import com.example.aad_project.dto.CustomerRegisterDTO;
import com.example.aad_project.entity.Customer;
import com.example.aad_project.entity.Role;
import com.example.aad_project.entity.User;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.CustomerRepository;
import com.example.aad_project.repository.RoleRepository;
import com.example.aad_project.repository.UserRepository;
import com.example.aad_project.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerCustomer(CustomerRegisterDTO registerDTO) {

        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent())
            throw new CustomException(409, "Username already taken");

        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new CustomException(500, "CUSTOMER role not configured"));

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setUserRoles(customerRole);
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setFullName(registerDTO.getFullName());
        customer.setAddress(registerDTO.getAddress());
        customerRepository.save(customer);

        log.info("New customer registered: {}", user.getUsername());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public List<CustomerDTO> filterCustomers(String fullName) {
        return customerRepository.filterCustomers(fullName);
    }

    @Override
    public CustomerDTO selectCustomer(long customerId) {
        return customerRepository.selectCustomer(customerId)
                .orElseThrow(() -> new CustomException(404, "Customer not found"));
    }

    @Override
    public CustomerDTO getMyProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(404, "User not found"));
        Customer customer = customerRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new CustomException(404, "Customer profile not found"));
        return new CustomerDTO(customer.getCustomerId(), user.getUserId(), user.getUsername(),
                customer.getFullName(), customer.getAddress());
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getCustomerId())
                .orElseThrow(() -> new CustomException(404, "Customer not found"));

        customer.setFullName(customerDTO.getFullName());
        customer.setAddress(customerDTO.getAddress());
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(long customerId) {
        if (!customerRepository.existsById(customerId))
            throw new CustomException(404, "Customer not found");
        customerRepository.deleteById(customerId);
    }}
