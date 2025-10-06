package com.example.backendapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@SpringBootApplication
@RestController
public class BackendappApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BackendappApplication.class, args);
    }
    
    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
            "status", "OK",
            "message", "Backend funcionando en Amplify!",
            "timestamp", Instant.now().toString()
        );
    }
    
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}