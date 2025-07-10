package com.lian.marketing.transactionmicroservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
class WebClientConfig {

    private final MicroservicesConfig url;

    @Bean(name = "stockWebClient")
    public WebClient stockWebClient() {
        return WebClient.builder()
                .baseUrl(url.getStock())
                .build();
    }

    @Bean(name = "userWebClient")
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl(url.getUser())
                .build();
    }

    @Bean(name = "paymentWebClient")
    public WebClient paymentWebClient(){
        return WebClient.builder()
                .baseUrl(url.getPayment())
                .build();
    }

    @Bean(name = "notificationWebClient")
    public WebClient notificationWebClient() {
        return WebClient.builder()
                .baseUrl(url.getNotification())
                .build();
    }

}
