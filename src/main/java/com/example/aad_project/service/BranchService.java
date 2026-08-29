package com.example.aad_project.service;

import com.example.aad_project.dto.request.BranchCreateRequestDTO;
import com.example.aad_project.dto.request.BranchUpdateRequestDTO;
import com.example.aad_project.dto.response.BranchResponseDTO;

import java.util.List;

public interface BranchService {

    void saveBranch(BranchCreateRequestDTO request);

    List<BranchResponseDTO> getAllBranches();

    List<BranchResponseDTO> filterBranches(String name);

    BranchResponseDTO selectBranch(long branchId);

    void updateBranch(BranchUpdateRequestDTO request);

    void deleteBranch(long branchId);
}
