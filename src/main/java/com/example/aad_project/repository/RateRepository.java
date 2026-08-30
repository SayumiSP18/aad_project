package com.example.aad_project.repository;

import com.example.aad_project.dto.RateDTO;
import com.example.aad_project.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query(value = "SELECT new com.example.aad_project.dto.RateDTO(r.rateId, r.zone.zoneId, r.zone.zoneName, " +
            "r.weightFrom, r.weightTo, r.pricePerKg) FROM Rate r")
    List<RateDTO> getAllRates();

    @Query(value = "SELECT new com.example.aad_project.dto.RateDTO(r.rateId, r.zone.zoneId, r.zone.zoneName, " +
            "r.weightFrom, r.weightTo, r.pricePerKg) FROM Rate r WHERE r.rateId = :rateId")
    Optional<RateDTO> selectRate(@Param("rateId") long rateId);

    @Query(value = "SELECT new com.example.aad_project.dto.RateDTO(r.rateId, r.zone.zoneId, r.zone.zoneName, " +
            "r.weightFrom, r.weightTo, r.pricePerKg) FROM Rate r " +
            "WHERE (:zoneId IS NULL OR r.zone.zoneId = :zoneId)")
    List<RateDTO> filterRates(@Param("zoneId") Long zoneId);}
