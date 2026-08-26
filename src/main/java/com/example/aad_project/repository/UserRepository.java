package com.example.aad_project.repository;

import com.example.aad_project.dto.UserDTO;
import com.example.aad_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserNameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT new com.example.aad_project.dto.UserDTO(u.userId, u.username, u.userRoles.roleName) " +
            "FROM User u")
    List<UserDTO> getAllUsers();

    @Query(value = "SELECT new com.example.aad_project.dto.UserDTO(u.userId, u.username, u.userRoles.roleName) " +
            "FROM User u " +
            "WHERE (:username IS NULL OR u.username LIKE %:username%)")
    List<UserDTO> filterUser(@Param("username") String username);

    @Query(value = "SELECT new com.example.aad_project.dto.UserDTO(u.userId, u.username, u.userRoles.roleName) " +
            "FROM User u WHERE u.userId = :userId")
    Optional<UserDTO> selectUser(@Param("userId") long userId);}
