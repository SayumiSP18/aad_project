package com.example.aad_project.repository;

import com.example.aad_project.dto.DeliveryDTO;
import com.example.aad_project.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
