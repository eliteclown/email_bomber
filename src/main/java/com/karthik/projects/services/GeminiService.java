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

//@Service
//public class GeminiService {
//    @Value("${gemini.apiKey}")
//    private String geminiApiKey;
//
//    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent";
//
//    public String generateEmailBody(int index) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Gemini prompt customization
//        String prompt = "Generate a friendly, engaging, and human-like short email body. "
//                + "Mention that this is email number " + index + ". Keep it unique and non-repetitive.";
//
//        Map<String, Object> requestBody = Map.of(
//                "contents", List.of(Map.of(
//                        "parts", List.of(Map.of("text", prompt))
//                ))
//        );
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(
//                GEMINI_URL +"?key="+ geminiApiKey,
//                request,
//                Map.class
//        );
//
//        try {
//            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
//            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
//            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
//            return (String) parts.get(0).get("text");
//        } catch (Exception e) {
//            return "This is fallback content for email #" + index;
//        }
//    }
//}


//@Service
//public class GeminiService {
//    @Value("${gemini.apiKey}")
//    private String geminiApiKey;
//
//    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent";
//
//    public String generateEmailBody(int index) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        String prompt = "Generate a friendly, engaging, and human-like short email body. "
//                + "Mention that this is email number " + index + ". Keep it unique and non-repetitive.";
//
//        Map<String, Object> requestBody = Map.of(
//                "contents", List.of(Map.of(
//                        "parts", List.of(Map.of("text", prompt))
//                ))
//        );
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
//
//        try {
//            ResponseEntity<Map> response = restTemplate.postForEntity(
//                    GEMINI_URL + "?key=" + geminiApiKey,
//                    request,
//                    Map.class
//            );
//
//            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
//            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
//            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
//            return (String) parts.get(0).get("text");
//
//        } catch (Exception e) {
//            return "This is fallback content for email #" + index;
//        }
//    }
//}



@Service
public class GeminiService {
    @Value("${gemini.apiKey}")
    private String geminiApiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent\n";

    public String generateEmailBody(int index) {
        RestTemplate restTemplate = new RestTemplate();

        String prompt = "Generate a friendly, engaging, and human-like short email body. "
                + "Mention that this is email number " + index + ". Keep it unique and non-repetitive.";

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    GEMINI_URL + "?key=" + geminiApiKey,
                    request,
                    Map.class
            );

            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("candidates")) {
                throw new RuntimeException("Invalid response: no candidates field.");
            }

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            if (candidates.isEmpty()) {
                throw new RuntimeException("No candidates returned.");
            }

            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            if (content == null || !content.containsKey("parts")) {
                throw new RuntimeException("Invalid candidate content structure.");
            }

            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            if (parts.isEmpty() || !parts.get(0).containsKey("text")) {
                throw new RuntimeException("Invalid parts or no text found.");
            }

            return (String) parts.get(0).get("text");

        } catch (Exception e) {
            e.printStackTrace();  // Log the exact error
            return "This is fallback content for email #" + index;
        }
    }
}
