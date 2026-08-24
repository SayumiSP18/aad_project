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

import java.util.List;

@RestController
@RequestMapping(value = "v1/login")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping(value = "/test")
    public String testSecurity(){
        return "API Security Successful";
    }

    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse authLogin(@RequestBody AuthDTO authDTO){
        UserDTO userDetails = userService.getUserDetails(authDTO.getUserName(), authDTO.getPassword());
        System.out.println("API called here");
        String token = jwtUtil.generateToken(userDetails);

        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setUserId(userDetails.getUserId());
        userDataDTO.setToken(token);

        return new CommonResponse(0,userDataDTO,"JWT Token");
    }

    @PostMapping(value = "/save-user",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return new CommonResponse(0,"User Saved");
    }

    @GetMapping(value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllUsers(){
        List<UserDTO> allUsers = userService.getAllUsers();
        return new CommonResponse(0,allUsers,"Get All users API");
    }

    @GetMapping(value = "/filter-users",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterUsers(@RequestParam(value = "userName",required = false)String userName){

        List<UserDTO> userDTOS = userService.filterUsers(userName);
        return new CommonResponse(0,userDTOS,"Get Filter users API");

    }

    @DeleteMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteUser(@PathVariable long userId){
        return new CommonResponse(0,"USER DELETED");
    }

    @GetMapping(value = "/select-user/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectUser(@PathVariable long userId){
        UserDTO dto = userService.selectUser(userId);
        return new CommonResponse(0,dto,"USER SELECTED");
    }

    @PutMapping(value = "/update-user",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new CommonResponse(0,"USER UPDATED");
    }


}
