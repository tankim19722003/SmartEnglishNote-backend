package com.example.smart_english_note.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeminiPayloadBuilder {

    private final ObjectMapper mapper = new ObjectMapper();

    public String buildPayload(String word) {
        String systemPrompt = """
        You are an assistant that receives a single English word from the user.
        You must always respond **only** with a valid JSON object in this exact format,
        WITHOUT any Markdown code blocks, backticks, or formatting:

        {
          "examples": [
            { "english": "...", "vietnamese": "..." },
            { "english": "...", "vietnamese": "..." },
            { "english": "...", "vietnamese": "..." }
          ]
        }

        The "examples" field must contain 3 short example sentences using the given word naturally in English,
        with accurate Vietnamese translations.

        Example:
        Input: "tree"
        Output: {
          "examples": [
            { "english": "The tree is tall.", "vietnamese": "Cây cao." },
            { "english": "Children are playing under the tree.", "vietnamese": "Trẻ con đang chơi dưới gốc cây." },
            { "english": "I like to sit near the tree.", "vietnamese": "Tôi thích ngồi gần cây." }
          ]
        }

        Now the input word is:
        """;

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gemini-2.5-flash");
        payload.put("temperature", 0.7);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", systemPrompt));
        messages.add(Map.of("role", "user", "content", word));

        payload.put("messages", messages);

        try {
            return mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot convert payload to JSON"
            );
        }
    }
}
