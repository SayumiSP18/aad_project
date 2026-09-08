package com.example.aad_project.service.impl;

import com.example.aad_project.dto.DeliveryDTO;
import com.example.aad_project.entity.*;
import com.example.aad_project.enumaration.ParcelStatus;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.*;
import com.example.aad_project.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final ParcelRepository parcelRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final RouteRepository routeRepository;

    @Override
    public void saveDelivery(DeliveryDTO deliveryDTO) {
        if (deliveryRepository.findByParcel_ParcelId(deliveryDTO.getParcelId()).isPresent())
            throw new CustomException(409, "Delivery already assigned for this parcel");

        Parcel parcel = parcelRepository.findById(deliveryDTO.getParcelId())
                .orElseThrow(() -> new CustomException(404, "Parcel not found"));
        Driver driver = driverRepository.findById(deliveryDTO.getDriverId())
                .orElseThrow(() -> new CustomException(404, "Driver not found"));
        Vehicle vehicle = vehicleRepository.findById(deliveryDTO.getVehicleId())
                .orElseThrow(() -> new CustomException(404, "Vehicle not found"));

        Delivery delivery = new Delivery();
        delivery.setParcel(parcel);
        delivery.setDriver(driver);
        delivery.setVehicle(vehicle);

        if (deliveryDTO.getRouteId() != null) {
            Route route = routeRepository.findById(deliveryDTO.getRouteId())
                    .orElseThrow(() -> new CustomException(404, "Route not found"));
            delivery.setRoute(route);
        }

        delivery.setStatus(ParcelStatus.OUT_FOR_DELIVERY);
        deliveryRepository.save(delivery);
        log.info("New delivery assigned for parcel: {}", parcel.getTrackingNo());
    }

    @Override
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.getAllDeliveries();
    }

    @Override
    public List<DeliveryDTO> filterDeliveries(Long driverId) {
        return deliveryRepository.filterDeliveries(driverId);
    }

    @Override
    public DeliveryDTO selectDelivery(long deliveryId) {
        return deliveryRepository.selectDelivery(deliveryId)
                .orElseThrow(() -> new CustomException(404, "Delivery not found"));
    }

    @Override
    public void updateDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryRepository.findById(deliveryDTO.getDeliveryId())
                .orElseThrow(() -> new CustomException(404, "Delivery not found"));

        if (deliveryDTO.getStatus() != null)
            delivery.setStatus(deliveryDTO.getStatus());

        if (deliveryDTO.getDeliveredAt() != null)
            delivery.setDeliveredAt(deliveryDTO.getDeliveredAt());

        deliveryRepository.save(delivery);
    }

    public void updateDeliveryStatus(long deliveryId, ParcelStatus status) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new CustomException(404, "Delivery not found"));

        delivery.setStatus(status);
        deliveryRepository.save(delivery);
    }

    @Override
    public void deleteDelivery(long deliveryId) {
        if (!deliveryRepository.existsById(deliveryId))
            throw new CustomException(404, "Delivery not found");
        deliveryRepository.deleteById(deliveryId);
    }}
