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
        try {
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
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save complaint for customer {}: {}", complaintDTO.getCustomerId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to file complaint");
        }
    }

    @Override
    public List<ComplaintDTO> getAllComplaints() {
        try {
            return complaintRepository.getAllComplaints();
        } catch (Exception e) {
            log.error("Failed to fetch complaints: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch complaints");
        }
    }

    @Override
    public List<ComplaintDTO> filterComplaints(Long customerId, ComplaintStatus status) {
        try {
            return complaintRepository.filterComplaints(customerId, status);
        } catch (Exception e) {
            log.error("Failed to filter complaints (customerId={}, status={}): {}",
                    customerId, status, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter complaints");
        }
    }

    @Override
    public ComplaintDTO selectComplaint(long complaintId) {
        try {
            return complaintRepository.selectComplaint(complaintId)
                    .orElseThrow(() -> new CustomException(404, "Complaint not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch complaint {}: {}", complaintId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch complaint");
        }
    }

    @Override
    public void updateComplaint(ComplaintDTO complaintDTO) {
        try {
            Complaint complaint = complaintRepository.findById(complaintDTO.getComplaintId())
                    .orElseThrow(() -> new CustomException(404, "Complaint not found"));

            complaint.setDescription(complaintDTO.getDescription());

            if (complaintDTO.getStatus() != null)
                complaint.setStatus(complaintDTO.getStatus());

            complaintRepository.save(complaint);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update complaint {}: {}", complaintDTO.getComplaintId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update complaint");
        }
    }

    @Override
    public void deleteComplaint(long complaintId) {
        try {
            if (!complaintRepository.existsById(complaintId))
                throw new CustomException(404, "Complaint not found");
            complaintRepository.deleteById(complaintId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete complaint {}: {}", complaintId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete complaint");
        }
    }
}