package com.example.aad_project.repository;

import com.example.aad_project.dto.NotificationDTO;
import com.example.aad_project.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT new com.example.aad_project.dto.NotificationDTO(n.notificationId, n.user.userId, n.user.username, " +
            "n.message, n.isRead, n.createdAt) FROM Notification n")
    List<NotificationDTO> getAllNotifications();

    @Query(value = "SELECT new com.example.aad_project.dto.NotificationDTO(n.notificationId, n.user.userId, n.user.username, " +
            "n.message, n.isRead, n.createdAt) FROM Notification n WHERE n.notificationId = :notificationId")
    Optional<NotificationDTO> selectNotification(@Param("notificationId") long notificationId);

    @Query(value = "SELECT new com.example.aad_project.dto.NotificationDTO(n.notificationId, n.user.userId, n.user.username, " +
            "n.message, n.isRead, n.createdAt) FROM Notification n " +
            "WHERE (:userId IS NULL OR n.user.userId = :userId) ORDER BY n.createdAt DESC")
    List<NotificationDTO> filterNotifications(@Param("userId") Long userId);
}
