package com.example.smart_english_note.chat.boundary.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExampleResponse {
    public String english;
    public String vietnamese;
}
