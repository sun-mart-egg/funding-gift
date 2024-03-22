package com.d201.fundingift._common.config;

import com.eni.backend.auth.jwt.JwtAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${base-url}")
    private String baseUrl;

//    private final JwtAuthInterceptor jwtAuthenticationInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtAuthenticationInterceptor)
//                .order(1)
//                .addPathPatterns("/api/members/**",
//                        "/api/problems/{problem-id}/codes/**",
//                        "/api/items/members/**",
//                        "/api/rooms/{room-id}/rank");
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", baseUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .maxAge(3000);
    }

}