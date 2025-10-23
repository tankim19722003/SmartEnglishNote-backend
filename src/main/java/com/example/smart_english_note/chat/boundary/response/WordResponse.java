package com.example.smart_english_note.chat.boundary.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WordResponse {

    private String word;

    private ExampleResponse[] examples;

    public String imageUrl;
}
