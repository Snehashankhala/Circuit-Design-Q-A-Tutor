package com.sneha.Circuit.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AIService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getCircuitExplanation(String question) {
        String systemPrompt = """
            You are an expert electronics and circuit design tutor.
            When a student describes a circuit problem, you must:
            1. Explain the relevant theory clearly
            2. Walk through all calculations step-by-step with formulas
            3. Suggest design improvements or common mistakes to avoid
            4. Use structured sections: Theory | Calculations | Design Tips
            Keep explanations beginner-friendly but technically accurate.
            """;

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", List.of(
            Map.of("role", "system", "content", systemPrompt),
            Map.of("role", "user", "content", question)
        ));
        body.put("max_tokens", 1500);
        body.put("temperature", 0.5);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

        List<Map> choices = (List<Map>) response.getBody().get("choices");
        Map message = (Map) choices.get(0).get("message");
        return (String) message.get("content");
    }

    public String analyzeCircuitImage(MultipartFile file) throws Exception {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        String mediaType = file.getContentType();
        String dataUrl = "data:" + mediaType + ";base64," + base64Image;

        Map<String, Object> imageUrlMap = new HashMap<>();
        imageUrlMap.put("url", dataUrl);

        Map<String, Object> imageContent = new HashMap<>();
        imageContent.put("type", "image_url");
        imageContent.put("image_url", imageUrlMap);

        Map<String, Object> textContent = new HashMap<>();
        textContent.put("type", "text");
        textContent.put("text", """
            Analyze this circuit image and provide:
            1. Components identified (resistors, capacitors, etc.)
            2. Circuit type and topology
            3. How the circuit works
            4. Potential issues or improvements
            5. Estimated values if visible
            Be detailed and educational.
            """);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "meta-llama/llama-4-scout-17b-16e-instruct");
        body.put("messages", List.of(
            Map.of("role", "user", "content", List.of(imageContent, textContent))
        ));
        body.put("max_tokens", 1500);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

        List<Map> choices = (List<Map>) response.getBody().get("choices");
        Map message = (Map) choices.get(0).get("message");
        return (String) message.get("content");
    }
}