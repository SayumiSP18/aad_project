package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.AuthDTO;
import com.example.aad_project.dto.UserDTO;
import com.example.aad_project.dto.UserDataDTO;
import com.example.aad_project.security.JwtUtil;
import com.example.aad_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Login endpoint used by login.html
     * Frontend calls: POST /v1/auth/login
     * Body: { "userName": "...", "password": "..." }
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse login(@RequestBody AuthDTO authDTO) {

        // Authenticate user (throws exception on invalid credentials)
        UserDTO userDetails = userService.authenticate(authDTO);

        // Generate JWT (contains userId, role, username)
        String token = jwtUtil.generateToken(userDetails);

        // Response body expected by frontend
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setUserId(userDetails.getUserId());
        userDataDTO.setToken(token);

        return new CommonResponse(0, userDataDTO, "JWT Token generated successfully");
    }
}