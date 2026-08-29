package com.example.aad_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponseDTO {
    private long branchId;
    private long zoneId;
    private String zoneName;
    private String name;
    private String address;
}
