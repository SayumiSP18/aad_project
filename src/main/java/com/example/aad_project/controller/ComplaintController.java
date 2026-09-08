package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.ComplaintDTO;
import com.example.aad_project.enumaration.ComplaintStatus;
import com.example.aad_project.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/complaints")
@CrossOrigin
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveComplaint( @RequestBody ComplaintDTO complaintDTO) {
        complaintService.saveComplaint(complaintDTO);
        return new CommonResponse(0, "Complaint filed successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllComplaints() {
        List<ComplaintDTO> complaints = complaintService.getAllComplaints();
        return new CommonResponse(0, complaints, "Get all complaints");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterComplaints(@RequestParam(value = "customerId", required = false) Long customerId,
                                           @RequestParam(value = "status", required = false) ComplaintStatus status) {
        List<ComplaintDTO> complaints = complaintService.filterComplaints(customerId, status);
        return new CommonResponse(0, complaints, "Filter complaints");
    }

    @GetMapping(value = "/{complaintId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectComplaint(@PathVariable long complaintId) {
        ComplaintDTO dto = complaintService.selectComplaint(complaintId);
        return new CommonResponse(0, dto, "Complaint details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateComplaint( @RequestBody ComplaintDTO complaintDTO) {
        complaintService.updateComplaint(complaintDTO);
        return new CommonResponse(0, "Complaint updated");
    }

    @DeleteMapping(value = "/{complaintId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteComplaint(@PathVariable long complaintId) {
        complaintService.deleteComplaint(complaintId);
        return new CommonResponse(0, "Complaint deleted");
    }

}
