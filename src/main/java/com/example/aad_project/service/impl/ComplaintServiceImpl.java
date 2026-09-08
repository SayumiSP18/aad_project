package com.example.aad_project.service.impl;

import com.example.aad_project.dto.ComplaintDTO;
import com.example.aad_project.entity.Complaint;
import com.example.aad_project.entity.Customer;
import com.example.aad_project.entity.Parcel;
import com.example.aad_project.enumaration.ComplaintStatus;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.ComplaintRepository;
import com.example.aad_project.repository.CustomerRepository;
import com.example.aad_project.repository.ParcelRepository;
import com.example.aad_project.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final CustomerRepository customerRepository;
    private final ParcelRepository parcelRepository;

    @Override
    public void saveComplaint(ComplaintDTO complaintDTO) {
        Customer customer = customerRepository.findById(complaintDTO.getCustomerId())
                .orElseThrow(() -> new CustomException(404, "Customer not found"));
        Parcel parcel = parcelRepository.findById(complaintDTO.getParcelId())
                .orElseThrow(() -> new CustomException(404, "Parcel not found"));

        Complaint complaint = new Complaint();
        complaint.setCustomer(customer);
        complaint.setParcel(parcel);
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setStatus(ComplaintStatus.OPEN);
        complaint.setCreatedAt(LocalDateTime.now());
        complaintRepository.save(complaint);
        log.info("New complaint filed by customer: {}", customer.getFullName());
    }

    @Override
    public List<ComplaintDTO> getAllComplaints() {
        return complaintRepository.getAllComplaints();
    }

    @Override
    public List<ComplaintDTO> filterComplaints(Long customerId, ComplaintStatus status) {
        return complaintRepository.filterComplaints(customerId, status);
    }

    @Override
    public ComplaintDTO selectComplaint(long complaintId) {
        return complaintRepository.selectComplaint(complaintId)
                .orElseThrow(() -> new CustomException(404, "Complaint not found"));
    }

    @Override
    public void updateComplaint(ComplaintDTO complaintDTO) {
        Complaint complaint = complaintRepository.findById(complaintDTO.getComplaintId())
                .orElseThrow(() -> new CustomException(404, "Complaint not found"));

        complaint.setDescription(complaintDTO.getDescription());

        if (complaintDTO.getStatus() != null)
            complaint.setStatus(complaintDTO.getStatus());

        complaintRepository.save(complaint);
    }

    @Override
    public void deleteComplaint(long complaintId) {
        if (!complaintRepository.existsById(complaintId))
            throw new CustomException(404, "Complaint not found");
        complaintRepository.deleteById(complaintId);
    }}
