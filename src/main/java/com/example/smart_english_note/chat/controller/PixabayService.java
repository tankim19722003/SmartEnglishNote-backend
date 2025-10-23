package com.example.smart_english_note.chat.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

@Service
public class PixabayService {

    @Value("${pixabay.api.key}")
    private String apiKey;

    private static final String API_URL = "https://pixabay.com/api/?key=%s&q=%s&image_type=photo&per_page=3";

    public String getImageUrl(String keyword) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(API_URL, apiKey, keyword);

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("hits")) {
            Object hitsObj = body.get("hits");
            if (hitsObj instanceof java.util.List<?> hits && !hits.isEmpty()) {
                Map<?, ?> firstHit = (Map<?, ?>) hits.get(0);
                return (String) firstHit.get("largeImageURL"); // hoặc "webformatURL", "previewURL"
            }
        }
        return null; // nếu không có ảnh
    }
}
