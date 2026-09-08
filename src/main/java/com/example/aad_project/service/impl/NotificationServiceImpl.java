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
        User user = userRepository.findById(notificationDTO.getUserId())
                .orElseThrow(() -> new CustomException(404, "User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(notificationDTO.getMessage());
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
        log.info("New notification sent to user: {}", user.getUsername());
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.getAllNotifications();
    }

    @Override
    public List<NotificationDTO> filterNotifications(Long userId) {
        return notificationRepository.filterNotifications(userId);
    }

    @Override
    public NotificationDTO selectNotification(long notificationId) {
        return notificationRepository.selectNotification(notificationId)
                .orElseThrow(() -> new CustomException(404, "Notification not found"));
    }

    @Override
    public void updateNotification(NotificationDTO notificationDTO) {

    }

    @Override
    public void deleteNotification(long notificationId) {

    }

}
