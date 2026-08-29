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
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new CustomException(404, "Zone not found"));

        Branch branch = new Branch();
        branch.setZone(zone);
        branch.setName(request.getName());
        branch.setAddress(request.getAddress());
        branchRepository.save(branch);
        log.info("New branch created: {}", branch.getName());
    }

    @Override
    public List<BranchResponseDTO> getAllBranches() {
        return branchRepository.getAllBranches();
    }

    @Override
    public List<BranchResponseDTO> filterBranches(String name) {
        return branchRepository.filterBranches(name);
    }

    @Override
    public BranchResponseDTO selectBranch(long branchId) {
        return branchRepository.selectBranch(branchId)
                .orElseThrow(() -> new CustomException(404, "Branch not found"));
    }

    @Override
    public void updateBranch(BranchUpdateRequestDTO request) {
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
    }

    @Override
    public void deleteBranch(long branchId) {
        if (!branchRepository.existsById(branchId))
            throw new CustomException(404, "Branch not found");
        branchRepository.deleteById(branchId);
    }
}
