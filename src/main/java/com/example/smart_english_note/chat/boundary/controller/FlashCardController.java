package com.example.smart_english_note.chat.boundary.controller;

import com.example.smart_english_note.chat.boundary.response.WordResponse;
import com.example.smart_english_note.chat.controller.FlashCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flashcard")
public class FlashCardController {

    private final FlashCardService flashCardService;

    @GetMapping("")
    public WordResponse create(@RequestParam("word") String question) {
        return flashCardService.create(question);
    }

}
