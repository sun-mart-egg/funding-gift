package com.d201.fundingift._common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "😺 생선가게 API",
                description = "토큰이 필요한 API는 발급된 accessToken을 Header에 담아서 보내주세요. \n" +
                        "ex)\n" +
                        "headers: { \n" +
                        "Authorization: Bearer ${token},\n" +
                "},\n" +
                "Swagger로 테스트 하려면 우측 Authorize 버튼을 누르고 토큰 입력 후 테스트 하시면 됩니다.\n"
        ),
        // security 설정 추가
        security = @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
)
@SecurityScheme(
        name = HttpHeaders.AUTHORIZATION,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI();
    }
}