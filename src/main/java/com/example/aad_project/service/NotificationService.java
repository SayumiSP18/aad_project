package com.example.aad_project.service;

import com.example.aad_project.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    void saveNotification(NotificationDTO notificationDTO);

    List<NotificationDTO> getAllNotifications();

    List<NotificationDTO> filterNotifications(Long userId);

    NotificationDTO selectNotification(long notificationId);

    void updateNotification(NotificationDTO notificationDTO);

    void deleteNotification(long notificationId);

}
