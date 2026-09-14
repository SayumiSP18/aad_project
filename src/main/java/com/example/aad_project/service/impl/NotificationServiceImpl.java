package com.example.aad_project.service.impl;

import com.example.aad_project.dto.NotificationDTO;
import com.example.aad_project.entity.Notification;
import com.example.aad_project.entity.User;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.NotificationRepository;
import com.example.aad_project.repository.UserRepository;
import com.example.aad_project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public void saveNotification(NotificationDTO notificationDTO) {
        try {
            User user = userRepository.findById(notificationDTO.getUserId())
                    .orElseThrow(() -> new CustomException(404, "User not found"));

            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(notificationDTO.getMessage());
            notification.setRead(false);
            notification.setCreatedAt(LocalDateTime.now());
            notificationRepository.save(notification);
            log.info("New notification sent to user: {}", user.getUsername());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save notification for user {}: {}", notificationDTO.getUserId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to send notification");
        }
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        try {
            return notificationRepository.getAllNotifications();
        } catch (Exception e) {
            log.error("Failed to fetch notifications: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch notifications");
        }
    }

    @Override
    public List<NotificationDTO> filterNotifications(Long userId) {
        try {
            return notificationRepository.filterNotifications(userId);
        } catch (Exception e) {
            log.error("Failed to filter notifications by user {}: {}", userId, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter notifications");
        }
    }

    @Override
    public NotificationDTO selectNotification(long notificationId) {
        try {
            return notificationRepository.selectNotification(notificationId)
                    .orElseThrow(() -> new CustomException(404, "Notification not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch notification {}: {}", notificationId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch notification");
        }
    }

    @Override
    public void updateNotification(NotificationDTO notificationDTO) {
        try {
            Notification notification = notificationRepository.findById(notificationDTO.getNotificationId())
                    .orElseThrow(() -> new CustomException(404, "Notification not found"));

            notification.setRead(notificationDTO.isRead());
            notificationRepository.save(notification);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update notification {}: {}", notificationDTO.getNotificationId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update notification");
        }
    }

    @Override
    public void deleteNotification(long notificationId) {
        try {
            if (!notificationRepository.existsById(notificationId))
                throw new CustomException(404, "Notification not found");
            notificationRepository.deleteById(notificationId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete notification {}: {}", notificationId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete notification");
        }
    }

}