package com.example.aad_project.service;

import com.example.aad_project.dto.ComplaintDTO;
import com.example.aad_project.enumaration.ComplaintStatus;

import java.util.List;

public interface ComplaintService {
    void saveComplaint(ComplaintDTO complaintDTO);

    List<ComplaintDTO> getAllComplaints();

    List<ComplaintDTO> filterComplaints(Long customerId, ComplaintStatus status);

    ComplaintDTO selectComplaint(long complaintId);

    void updateComplaint(ComplaintDTO complaintDTO);

    void deleteComplaint(long complaintId);
}
