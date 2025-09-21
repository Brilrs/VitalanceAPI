package org.example.vitalance.servicios;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GeminiService {

    @Value("${ia.gemini.api.key}")
    private String apiKey;

    @Value("${ia.gemini.model}")
    private String model;

    public String generarTexto(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + model + ":generateContent?key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body de la request
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        content.put("parts", Collections.singletonList(Collections.singletonMap("text", prompt)));
        body.put("contents", Collections.singletonList(content));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            return root.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error procesando respuesta de Gemini: " + e.getMessage(), e);
        }
    }
}
