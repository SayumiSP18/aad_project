package com.example.aad_project.service;

import com.example.aad_project.dto.UserDTO;

import java.util.List;

public interface UserService {


    UserDTO getUserDetails(String username, String password);

    void saveUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    List<UserDTO> filterUsers(String username);

    UserDTO selectUser(long userId);

    void updateUser(UserDTO userDTO);}
