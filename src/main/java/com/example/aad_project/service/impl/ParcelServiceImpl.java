package com.example.aad_project.service.impl;

import com.example.aad_project.dto.ParcelDTO;
import com.example.aad_project.entity.Customer;
import com.example.aad_project.entity.Parcel;
import com.example.aad_project.enumaration.ParcelStatus;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.CustomerRepository;
import com.example.aad_project.repository.ParcelRepository;
import com.example.aad_project.service.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void saveParcel(ParcelDTO parcelDTO) {
        try {
            Customer customer = customerRepository.findById(parcelDTO.getCustomerId())
                    .orElseThrow(() -> new CustomException(404, "Customer not found"));

            Parcel parcel = new Parcel();
            parcel.setCustomer(customer);
            parcel.setTrackingNo("PCL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            parcel.setWeight(parcelDTO.getWeight());
            parcel.setDescription(parcelDTO.getDescription());
            parcel.setReceiverName(parcelDTO.getReceiverName());
            parcel.setReceiverAddress(parcelDTO.getReceiverAddress());
            parcel.setStatus(ParcelStatus.BOOKED);
            parcelRepository.save(parcel);
            log.info("New parcel created with tracking no: {}", parcel.getTrackingNo());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save parcel for customer {}: {}", parcelDTO.getCustomerId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to create parcel");
        }
    }

    @Override
    public List<ParcelDTO> getAllParcels() {
        try {
            return parcelRepository.getAllParcels();
        } catch (Exception e) {
            log.error("Failed to fetch parcels: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch parcels");
        }
    }

    @Override
    public List<ParcelDTO> filterParcels(Long customerId, String trackingNo) {
        try {
            return parcelRepository.filterParcels(customerId, trackingNo);
        } catch (Exception e) {
            log.error("Failed to filter parcels (customerId={}, trackingNo={}): {}",
                    customerId, trackingNo, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter parcels");
        }
    }

    @Override
    public ParcelDTO selectParcel(long parcelId) {
        try {
            return parcelRepository.selectParcel(parcelId)
                    .orElseThrow(() -> new CustomException(404, "Parcel not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch parcel {}: {}", parcelId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch parcel");
        }
    }

    @Override
    public void updateParcel(ParcelDTO parcelDTO) {
        try {
            Parcel parcel = parcelRepository.findById(parcelDTO.getParcelId())
                    .orElseThrow(() -> new CustomException(404, "Parcel not found"));

            parcel.setWeight(parcelDTO.getWeight());
            parcel.setDescription(parcelDTO.getDescription());
            parcel.setReceiverName(parcelDTO.getReceiverName());
            parcel.setReceiverAddress(parcelDTO.getReceiverAddress());

            if (parcelDTO.getStatus() != null)
                parcel.setStatus(parcelDTO.getStatus());

            parcelRepository.save(parcel);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update parcel {}: {}", parcelDTO.getParcelId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update parcel");
        }
    }

    @Override
    public void deleteParcel(long parcelId) {
        try {
            if (!parcelRepository.existsById(parcelId))
                throw new CustomException(404, "Parcel not found");
            parcelRepository.deleteById(parcelId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete parcel {}: {}", parcelId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete parcel");
        }
    }
}