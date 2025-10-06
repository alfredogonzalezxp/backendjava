package com.example.backendapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todas las rutas
            .allowedOrigins(
                "http://localhost:3000",           // React local
                "http://localhost:5173",           // Vite local
                "https://main.d5wyalixm0s6f.amplifyapp.com", // Producción
                "https://*.amplifyapp.com",        // Si usas Amplify
                "https://*.vercel.app",            // Si usas Vercel
                "https://*.netlify.app"            // Si usas Netlify
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
