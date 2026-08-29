package com.example.aad_project.repository;

import com.example.aad_project.dto.response.BranchResponseDTO;
import com.example.aad_project.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query(value = "SELECT new com.example.aad_project.dto.response.BranchResponseDTO(b.branchId, b.zone.zoneId, b.zone.zoneName, b.name, b.address) " +
            "FROM Branch b")
    List<BranchResponseDTO> getAllBranches();

    @Query(value = "SELECT new com.example.aad_project.dto.response.BranchResponseDTO(b.branchId, b.zone.zoneId, b.zone.zoneName, b.name, b.address) " +
            "FROM Branch b WHERE b.branchId = :branchId")
    Optional<BranchResponseDTO> selectBranch(@Param("branchId") long branchId);

    @Query(value = "SELECT new com.example.aad_project.dto.response.BranchResponseDTO(b.branchId, b.zone.zoneId, b.zone.zoneName, b.name, b.address) " +
            "FROM Branch b WHERE (:name IS NULL OR b.name LIKE %:name%)")
    List<BranchResponseDTO> filterBranches(@Param("name") String name);
}
