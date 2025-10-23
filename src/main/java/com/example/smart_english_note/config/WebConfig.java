package com.example.smart_english_note.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho tất cả endpoint
//                .allowedOrigins("https://tankim19722003.github.io") // frontend URL
                .allowedOrigins("https://englishsmartnote-frontend.onrender.com") // frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true) // nếu muốn gửi cookie/token
                .maxAge(3600);
    }
}
