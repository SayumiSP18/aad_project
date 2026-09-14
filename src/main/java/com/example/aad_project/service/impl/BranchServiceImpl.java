package com.example.aad_project.service.impl;

import com.example.aad_project.dto.request.BranchCreateRequestDTO;
import com.example.aad_project.dto.request.BranchUpdateRequestDTO;
import com.example.aad_project.dto.response.BranchResponseDTO;
import com.example.aad_project.entity.Branch;
import com.example.aad_project.entity.Zone;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.BranchRepository;
import com.example.aad_project.repository.ZoneRepository;
import com.example.aad_project.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final ZoneRepository zoneRepository;

    @Override
    public void saveBranch(BranchCreateRequestDTO request) {
        try {
            Zone zone = zoneRepository.findById(request.getZoneId())
                    .orElseThrow(() -> new CustomException(404, "Zone not found"));

            Branch branch = new Branch();
            branch.setZone(zone);
            branch.setName(request.getName());
            branch.setAddress(request.getAddress());
            branchRepository.save(branch);
            log.info("New branch created: {}", branch.getName());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save branch '{}': {}", request.getName(), e.getMessage(), e);
            throw new CustomException(500, "Failed to create branch");
        }
    }

    @Override
    public List<BranchResponseDTO> getAllBranches() {
        try {
            return branchRepository.getAllBranches();
        } catch (Exception e) {
            log.error("Failed to fetch branches: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch branches");
        }
    }

    @Override
    public List<BranchResponseDTO> filterBranches(String name) {
        try {
            return branchRepository.filterBranches(name);
        } catch (Exception e) {
            log.error("Failed to filter branches by name '{}': {}", name, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter branches");
        }
    }

    @Override
    public BranchResponseDTO selectBranch(long branchId) {
        try {
            return branchRepository.selectBranch(branchId)
                    .orElseThrow(() -> new CustomException(404, "Branch not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch branch {}: {}", branchId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch branch");
        }
    }

    @Override
    public void updateBranch(BranchUpdateRequestDTO request) {
        try {
            Branch branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new CustomException(404, "Branch not found"));

            if (request.getZoneId() != null) {
                Zone zone = zoneRepository.findById(request.getZoneId())
                        .orElseThrow(() -> new CustomException(404, "Zone not found"));
                branch.setZone(zone);
            }

            branch.setName(request.getName());
            branch.setAddress(request.getAddress());
            branchRepository.save(branch);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update branch {}: {}", request.getBranchId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update branch");
        }
    }

    @Override
    public void deleteBranch(long branchId) {
        try {
            if (!branchRepository.existsById(branchId))
                throw new CustomException(404, "Branch not found");
            branchRepository.deleteById(branchId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete branch {}: {}", branchId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete branch");
        }
    }
}