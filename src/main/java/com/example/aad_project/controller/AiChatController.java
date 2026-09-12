////package com.example.aad_project.controller;
////
////import com.example.aad_project.constant.CommonResponse;
////import com.example.aad_project.dto.ChatRequestDTO;
////import com.example.aad_project.dto.ChatResponseDTO;
////import com.example.aad_project.service.AiChatService;
////import jakarta.validation.Valid;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.security.core.Authentication;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.RequestBody;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
////
////@RestController
////@RequestMapping("/api/chat")
////public class AiChatController {
////
////    @Autowired
////    private AiChatService aiChatService;
////
////    @PostMapping
////    @PreAuthorize("hasAnyRole('USER','GUEST','ADMIN')")
////    public ResponseEntity<CommonResponse<ChatResponseDTO>> chat(
////            @Valid @RequestBody ChatRequestDTO requestDto,
////            Authentication authentication) {
////
////        String username = authentication.getName();
////        ChatResponseDTO reply = aiChatService.getAssistantReply(username, requestDto.getMessage());
////
////        return ResponseEntity.ok(
////                new CommonResponse<>(true, "Reply generated", reply)
////        );
////    }
////}
//
//
package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.ChatRequestDTO;
import com.example.aad_project.dto.ChatResponseDTO;
import com.example.aad_project.service.AiChatService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','GUEST','ADMIN')")
    public ResponseEntity<CommonResponse> chat(
            @Valid @RequestBody ChatRequestDTO requestDto,
            Authentication authentication) {

        String username = authentication.getName();
        ChatResponseDTO reply = aiChatService.getAssistantReply(username, requestDto.getMessage());

        return ResponseEntity.ok(
                new CommonResponse(200, reply, "Reply generated")
        );
    }
}