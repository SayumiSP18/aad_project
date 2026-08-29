package com.example.aad_project.repository;

import com.example.aad_project.dto.response.ZoneResponseDTO;
import com.example.aad_project.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    @Query(value = "SELECT new com.example.aad_project.dto.response.ZoneResponseDTO(z.zoneId, z.zoneName) FROM Zone z")
    List<ZoneResponseDTO> getAllZones();

    @Query(value = "SELECT new com.example.aad_project.dto.response.ZoneResponseDTO(z.zoneId, z.zoneName) " +
            "FROM Zone z WHERE z.zoneId = :zoneId")
    Optional<ZoneResponseDTO> selectZone(@Param("zoneId") long zoneId);

    @Query(value = "SELECT new com.example.aad_project.dto.response.ZoneResponseDTO(z.zoneId, z.zoneName) " +
            "FROM Zone z WHERE (:zoneName IS NULL OR z.zoneName LIKE %:zoneName%)")
    List<ZoneResponseDTO> filterZones(@Param("zoneName") String zoneName);
}
