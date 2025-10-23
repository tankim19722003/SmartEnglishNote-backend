package com.example.smart_english_note.chat.controller;

import com.example.smart_english_note.chat.boundary.response.WordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlashCardService {

    private final GeminiService geminiService;

    private final PixabayService pixabayService;

    public WordResponse create(String word) {

        long startTime = System.currentTimeMillis();

        // Fetch word info asynchronously
        CompletableFuture<WordResponse> wordInfoFuture = CompletableFuture.supplyAsync(() -> geminiService.getWordInfo(word));

        // Fetch image URL asynchronously
        CompletableFuture<String> imageFuture = CompletableFuture.supplyAsync(() -> pixabayService.getImageUrl(word));

        // Combine results
        WordResponse wordResponse = wordInfoFuture.thenCombine(imageFuture, (wordInfo, imageUrl) -> {
            wordInfo.setImageUrl(imageUrl);
            wordInfo.setWord(word);
            return wordInfo;
        }).join(); // Wait for both tasks to complete

        long endTime = System.currentTimeMillis();
        log.info("Time for creating card: {}", endTime - startTime);

        return wordResponse;
    }
}
