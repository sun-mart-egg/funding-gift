package com.d201.fundingift._common.config;

import org.springframework.beans.factory.annotation.Value;
import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${imp.key}")
    String apiKey;

    @Value("${imp.secret}")
    String secretKey;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }
}
