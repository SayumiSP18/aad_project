package com.example.aad_project.service;

import com.example.aad_project.dto.ChatResponseDTO;

public interface AiChatService {
    ChatResponseDTO getAssistantReply(String username, String userMessage);
}