//package com.example.aad_project.repository;
//
//import com.example.aad_project.entity.Driver;
//import com.example.aad_project.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface DriverRepository extends JpaRepository<Driver, Long> {
//
////    Optional<Driver> findByUser_UserId(Long userId);
//Optional<Driver> findByUser_UserId(Long userId);
//    Optional<Driver> findByUserUsername(String username);
//
//    List<Driver> findByBranchBranchId(Long branchId);
//
//    List<Driver> findByAvailableTrue();
//
//    // DriverRepository.java
//    Optional<Driver> findByUser(User user);
//    Optional<Driver> findByUser_Username(String username);
//    Optional<Driver> findByUser_Id(Long userId);
//}
//

package com.example.aad_project.repository;

import com.example.aad_project.entity.Driver;
import com.example.aad_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByUser(User user);

    Optional<Driver> findByUser_UserId(Long userId);

    List<Driver> findByBranchBranchId(Long branchId);

    List<Driver> findByAvailableTrue();
}
