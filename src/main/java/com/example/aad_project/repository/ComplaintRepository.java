package com.example.aad_project.repository;

import com.example.aad_project.dto.ComplaintDTO;
import com.example.aad_project.entity.Complaint;
import com.example.aad_project.enumaration.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    @Query(value = "SELECT new com.example.aad_project.dto.ComplaintDTO(c.complaintId, c.customer.customerId, c.customer.fullName, " +
            "c.parcel.parcelId, c.parcel.trackingNo, c.description, c.status, c.createdAt) FROM Complaint c")
    List<ComplaintDTO> getAllComplaints();

    @Query(value = "SELECT new com.example.aad_project.dto.ComplaintDTO(c.complaintId, c.customer.customerId, c.customer.fullName, " +
            "c.parcel.parcelId, c.parcel.trackingNo, c.description, c.status, c.createdAt) FROM Complaint c WHERE c.complaintId = :complaintId")
    Optional<ComplaintDTO> selectComplaint(@Param("complaintId") long complaintId);

    @Query(value = "SELECT new com.example.aad_project.dto.ComplaintDTO(c.complaintId, c.customer.customerId, c.customer.fullName, " +
            "c.parcel.parcelId, c.parcel.trackingNo, c.description, c.status, c.createdAt) FROM Complaint c " +
            "WHERE (:customerId IS NULL OR c.customer.customerId = :customerId) AND (:status IS NULL OR c.status = :status)")
    List<ComplaintDTO> filterComplaints(@Param("customerId") Long customerId, @Param("status") ComplaintStatus status);
}
