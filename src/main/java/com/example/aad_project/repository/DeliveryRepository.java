package com.example.aad_project.repository;

import com.example.aad_project.dto.DeliveryDTO;
import com.example.aad_project.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByParcel_ParcelId(long parcelId);

    @Query(value = "SELECT new com.example.aad_project.dto.DeliveryDTO(d.deliveryId, d.parcel.parcelId, d.parcel.trackingNo, " +
            "d.driver.driverId, d.driver.user.username, d.vehicle.vehicleId, d.vehicle.vehicleNo, " +
            "d.route.routeId, d.status, d.deliveredAt) FROM Delivery d")
    List<DeliveryDTO> getAllDeliveries();

    @Query(value = "SELECT new com.example.aad_project.dto.DeliveryDTO(d.deliveryId, d.parcel.parcelId, d.parcel.trackingNo, " +
            "d.driver.driverId, d.driver.user.username, d.vehicle.vehicleId, d.vehicle.vehicleNo, " +
            "d.route.routeId, d.status, d.deliveredAt) FROM Delivery d WHERE d.deliveryId = :deliveryId")
    Optional<DeliveryDTO> selectDelivery(@Param("deliveryId") long deliveryId);

    @Query(value = "SELECT new com.example.aad_project.dto.DeliveryDTO(d.deliveryId, d.parcel.parcelId, d.parcel.trackingNo, " +
            "d.driver.driverId, d.driver.user.username, d.vehicle.vehicleId, d.vehicle.vehicleNo, " +
            "d.route.routeId, d.status, d.deliveredAt) FROM Delivery d " +
            "WHERE (:driverId IS NULL OR d.driver.driverId = :driverId)")
    List<DeliveryDTO> filterDeliveries(@Param("driverId") Long driverId);







    @Query("SELECT d.status, COUNT(d) FROM Delivery d GROUP BY d.status")
    List<Object[]> countByStatus();

    @Query("SELECT d.driver.user.username, COUNT(d) FROM Delivery d " +
            "WHERE d.status = 'DELIVERED' GROUP BY d.driver.user.username " +
            "ORDER BY COUNT(d) DESC")
    List<Object[]> topDrivers();

    @Query("SELECT d.driver.branch.name, COUNT(d) " +
            "FROM Delivery d GROUP BY d.driver.branch.name")
    List<Object[]> countByBranch();

    @Query("SELECT FUNCTION('DATE', d.deliveredAt), COUNT(d) " +
            "FROM Delivery d WHERE d.deliveredAt >= :since " +
            "GROUP BY FUNCTION('DATE', d.deliveredAt) ORDER BY FUNCTION('DATE', d.deliveredAt)")
    List<Object[]> countPerDaySince(@Param("since") LocalDateTime since);

}
