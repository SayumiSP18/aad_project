package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.request.BranchCreateRequestDTO;
import com.example.aad_project.dto.request.BranchUpdateRequestDTO;
import com.example.aad_project.dto.response.BranchResponseDTO;
import com.example.aad_project.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/branches")
@CrossOrigin
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveBranch( @RequestBody BranchCreateRequestDTO request) {
        branchService.saveBranch(request);
        return new CommonResponse(0, "Branch created successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllBranches() {
        List<BranchResponseDTO> branches = branchService.getAllBranches();
        return new CommonResponse(0, branches, "Get all branches");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterBranches(@RequestParam(value = "name", required = false) String name) {
        List<BranchResponseDTO> branches = branchService.filterBranches(name);
        return new CommonResponse(0, branches, "Filter branches");
    }

    @GetMapping(value = "/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectBranch(@PathVariable long branchId) {
        BranchResponseDTO response = branchService.selectBranch(branchId);
        return new CommonResponse(0, response, "Branch details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateBranch( @RequestBody BranchUpdateRequestDTO request) {
        branchService.updateBranch(request);
        return new CommonResponse(0, "Branch updated");
    }

    @DeleteMapping(value = "/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteBranch(@PathVariable long branchId) {
        branchService.deleteBranch(branchId);
        return new CommonResponse(0, "Branch deleted");
    }}
