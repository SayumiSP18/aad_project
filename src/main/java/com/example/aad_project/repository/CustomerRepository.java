package com.example.aad_project.repository;

import com.example.aad_project.dto.CustomerDTO;
import com.example.aad_project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUser_UserId(long userId);

    @Query(value = "SELECT new com.example.aad_project.dto.CustomerDTO(c.customerId, c.user.userId, c.user.username, " +
            "c.fullName, c.address) FROM Customer c")
    List<CustomerDTO> getAllCustomers();

    @Query(value = "SELECT new com.example.aad_project.dto.CustomerDTO(c.customerId, c.user.userId, c.user.username, " +
            "c.fullName, c.address) FROM Customer c WHERE c.customerId = :customerId")
    Optional<CustomerDTO> selectCustomer(@Param("customerId") long customerId);

    @Query(value = "SELECT new com.example.aad_project.dto.CustomerDTO(c.customerId, c.user.userId, c.user.username, " +
            "c.fullName, c.address) FROM Customer c WHERE (:fullName IS NULL OR c.fullName LIKE %:fullName%)")
    List<CustomerDTO> filterCustomers(@Param("fullName") String fullName);}
