package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private long notificationId;

//    @NotNull(message = "User is required")
    private Long userId;

    private String username;

//    @NotBlank(message = "Message is required")
    private String message;

    private boolean isRead;
    private LocalDateTime createdAt;

    public NotificationDTO(long notificationId, long userId, String username, String message,
                           boolean isRead, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.username = username;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }
}
