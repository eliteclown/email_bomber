package com.karthik.projects.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;

@Service
public class GeminiService {
    @Value("${gemini.apiKey}")
    private String geminiApiKey;

    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=";

    public String generateEmailBody(int index) {
        RestTemplate restTemplate = new RestTemplate();

        // Gemini prompt customization
        String prompt = "Generate a friendly, engaging, and human-like short email body. "
                + "Mention that this is email number " + index + ". Keep it unique and non-repetitive.";

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of("text", prompt))
                ))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                GEMINI_URL + geminiApiKey,
                request,
                Map.class
        );

        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            return "This is fallback content for email #" + index;
        }
    }
}
