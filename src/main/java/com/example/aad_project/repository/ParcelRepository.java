package com.example.aad_project.repository;

import com.example.aad_project.dto.ParcelDTO;
import com.example.aad_project.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    Optional<Parcel> findByTrackingNo(String trackingNo);

    @Query(value = "SELECT new com.example.aad_project.dto.ParcelDTO(p.parcelId, p.customer.customerId, p.customer.fullName, " +
            "p.trackingNo, p.weight, p.description, p.receiverName, p.receiverAddress, p.status) FROM Parcel p")
    List<ParcelDTO> getAllParcels();

    @Query(value = "SELECT new com.example.aad_project.dto.ParcelDTO(p.parcelId, p.customer.customerId, p.customer.fullName, " +
            "p.trackingNo, p.weight, p.description, p.receiverName, p.receiverAddress, p.status) " +
            "FROM Parcel p WHERE p.parcelId = :parcelId")
    Optional<ParcelDTO> selectParcel(@Param("parcelId") long parcelId);

    @Query(value = "SELECT new com.example.aad_project.dto.ParcelDTO(p.parcelId, p.customer.customerId, p.customer.fullName, " +
            "p.trackingNo, p.weight, p.description, p.receiverName, p.receiverAddress, p.status) " +
            "FROM Parcel p WHERE (:customerId IS NULL OR p.customer.customerId = :customerId) " +
            "AND (:trackingNo IS NULL OR p.trackingNo LIKE %:trackingNo%)")

    List<ParcelDTO> filterParcels(@Param("customerId") Long customerId, @Param("trackingNo") String trackingNo);

    List<Parcel> findTop5ByCustomer_User_UsernameOrderByParcelIdDesc(String username);
}
