package com.worknest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ frontend origin
        config.setAllowedOrigins(List.of(
        	    "http://localhost:5173",
        	    "http://localhost:3000",
        	    "https://your-frontend.vercel.app"
        	));

        // ✅ allow all HTTP methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ allow headers (Authorization, Content-Type, etc.)
        config.setAllowedHeaders(List.of("*"));

        // ✅ allow JWT Authorization header
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // ✅ apply to all endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}