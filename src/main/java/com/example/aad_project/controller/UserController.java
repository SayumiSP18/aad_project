package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.AuthDTO;
import com.example.aad_project.dto.UserDTO;
import com.example.aad_project.security.JwtUtil;
import com.example.aad_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/login")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping(value = "/login")
    public String testSecurity(){
        return "API Security Successful";
    }

    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse authLogin(@RequestBody AuthDTO authDTO){
        UserDTO userDetails = userService.getUserDetails(authDTO.getUserName(), authDTO.getPassword());
        System.out.println("API called here");
        String token = jwtUtil.generateToken(userDetails);
        return new CommonResponse(0,token,"JWT Token");
    }

    @PostMapping(value = "/save-user",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);

        return new CommonResponse(0,"User saved");
    }

    @GetMapping(value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllUsers(@RequestBody AuthDTO authDTO){
        List<UserDTO> allUsers = userService.getAllUsers();

        return new CommonResponse(0,allusers,"Get all users");
    }

    @GetMapping(value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllUsers(@RequestBody AuthDTO authDTO){
        List<UserDTO> allUsers = userService.getAllUsers();

        return new CommonResponse(0,allusers,"Get all users");
    }


}
