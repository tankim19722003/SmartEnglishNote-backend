package com.example.smart_english_note.chat.controller;

import com.example.smart_english_note.chat.boundary.response.WordResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    private final GeminiPayloadBuilder payloadBuilder;

    public GeminiService(GeminiPayloadBuilder payloadBuilder) {
        this.payloadBuilder = payloadBuilder;
    }

    public WordResponse getWordInfo(String word) {
        String payload = payloadBuilder.buildPayload(word);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/openai/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Error calling Gemini API: " + response.statusCode() + " " + response.body()
                );
            }

            return parseResponse(response.body());

        } catch (IOException | InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error calling Gemini API", e);
        }
    }

    private WordResponse parseResponse(String responseBody) throws IOException {
        JsonNode root = mapper.readTree(responseBody);
        JsonNode contentNode = root.path("choices").get(0).path("message").path("content");

        if (contentNode.isMissingNode()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid response from Gemini API");
        }

        return JsonConverter.toWordResponse(contentNode.asText());
    }
}
