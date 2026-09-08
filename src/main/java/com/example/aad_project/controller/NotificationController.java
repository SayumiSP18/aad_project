package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.NotificationDTO;
import com.example.aad_project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/notifications")
@CrossOrigin
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveNotification( @RequestBody NotificationDTO notificationDTO) {
        notificationService.saveNotification(notificationDTO);
        return new CommonResponse(0, "Notification sent successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return new CommonResponse(0, notifications, "Get all notifications");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterNotifications(@RequestParam(value = "userId", required = false) Long userId) {
        List<NotificationDTO> notifications = notificationService.filterNotifications(userId);
        return new CommonResponse(0, notifications, "Filter notifications");
    }

    @GetMapping(value = "/{notificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectNotification(@PathVariable long notificationId) {
        NotificationDTO dto = notificationService.selectNotification(notificationId);
        return new CommonResponse(0, dto, "Notification details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateNotification( @RequestBody NotificationDTO notificationDTO) {
        notificationService.updateNotification(notificationDTO);
        return new CommonResponse(0, "Notification updated");
    }

    @DeleteMapping(value = "/{notificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteNotification(@PathVariable long notificationId) {
        notificationService.deleteNotification(notificationId);
        return new CommonResponse(0, "Notification deleted");
    }}
