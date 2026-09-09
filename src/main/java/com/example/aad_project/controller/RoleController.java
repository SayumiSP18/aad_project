package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.RoleDTO;
import com.example.aad_project.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/roles")
@CrossOrigin
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveRole(@Valid @RequestBody RoleDTO roleDTO) {
        roleService.saveRole(roleDTO);
        return new CommonResponse(0, "Role created successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return new CommonResponse(0, roles, "Get all roles");
    }

    @GetMapping(value = "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectRole(@PathVariable long roleId) {
        RoleDTO dto = roleService.selectRole(roleId);
        return new CommonResponse(0, dto, "Role details");
    }

    @DeleteMapping(value = "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteRole(@PathVariable long roleId) {
        roleService.deleteRole(roleId);
        return new CommonResponse(0, "Role deleted");
    }
}
