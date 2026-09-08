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
@RequestMapping(value = "v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping(value = "/test")
    public String testSecurity() {
        return "API Security Successful";
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse authLogin(@RequestBody AuthDTO authDTO) {
        UserDTO userDetails = userService.authenticate(authDTO);
        String token = jwtUtil.generateToken(userDetails);

        UserDataDTO userDataDTO = new UserDataDTO(userDetails.getUserId(), token);
        return new CommonResponse(0, userDataDTO, "JWT Token");
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse register(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new CommonResponse(0, "User registered successfully");
    }
}
