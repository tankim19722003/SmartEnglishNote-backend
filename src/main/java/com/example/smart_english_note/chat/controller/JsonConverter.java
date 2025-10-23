package com.example.smart_english_note.chat.controller;

import com.example.smart_english_note.chat.boundary.response.WordResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JsonConverter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static WordResponse toWordResponse(String json) {
        try {
            return mapper.readValue(json, WordResponse.class);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot parse AI response to WordResponse: " + e.getMessage()
            );
        }
    }
}