package com.example.aad_project.service.impl;

import com.example.aad_project.dto.ChatResponseDTO;
import com.example.aad_project.entity.Parcel;
import com.example.aad_project.exception.AiServiceException;
import com.example.aad_project.repository.ParcelRepository;
import com.example.aad_project.service.AiChatService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AiChatServiceImpl implements AiChatService {

    private static final Logger logger = LoggerFactory.getLogger(AiChatServiceImpl.class);

    @Value("${ai.api.key}")
    private String apiKey;

    @Value("${ai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ParcelRepository parcelRepository;

    @Autowired
    public AiChatServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public ChatResponseDTO getAssistantReply(String username, String userMessage) {

        List<Parcel> recentParcels =
                parcelRepository.findTop5ByCustomer_User_UsernameOrderByParcelIdDesc(username);
        String context = buildParcelContext(recentParcels);

        String fullPrompt = """
            You are a helpful delivery assistant for a courier company.
            Answer only using the parcel data provided below. If the answer
            isn't in the data, say you don't have that information.
            Keep answers under 3 sentences.

            Parcel data:
            %s

            Customer question: %s
            """.formatted(context, userMessage);

        Map<String, Object> part = Map.of("text", fullPrompt);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> body = Map.of("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        String urlWithKey = apiUrl + "?key=" + apiKey;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    urlWithKey, HttpMethod.POST, request,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            String reply = extractTextFromResponse(response.getBody());
            logger.info("AI chat reply generated for user {}", username);
            return new ChatResponseDTO(reply, LocalDateTime.now());
        } catch (RestClientException e) {
            logger.error("AI API call failed: {}", e.getMessage());
            throw new AiServiceException("Assistant is currently unavailable, please try again later.");
        }
    }

    private String buildParcelContext(List<Parcel> parcels) {
        if (parcels.isEmpty()) return "This user has no parcels on record.";
        StringBuilder sb = new StringBuilder();
        for (Parcel p : parcels) {
            sb.append("Tracking No: %s, Status: %s, Receiver: %s, Address: %s%n"
                    .formatted(p.getTrackingNo(), p.getStatus(), p.getReceiverName(), p.getReceiverAddress()));
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private String extractTextFromResponse(Map<String, Object> responseBody) {
        List<Map<String, Object>> candidates =
                (List<Map<String, Object>>) responseBody.get("candidates");
        Map<String, Object> firstCandidate = candidates.get(0);
        Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        return (String) parts.get(0).get("text");
    }
}