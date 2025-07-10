package com.lian.marketing.transactionmicroservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class WebClientConfig {

    @Value("${microservice.url.stock}")
    private String stockMicroserviceUrl;

    @Value("${microservice.url.user}")
    private String userMicroserviceUrl;

    @Value("${microservice.url.payment}")
    private String paymentMicroserviceUrl;

    @Value("${microservice.url.notification}")
    private String notificationMicroserviceUrl;

    @Bean(name = "stockWebClient")
    public WebClient stockWebClient() {
        return WebClient.builder()
                .baseUrl(stockMicroserviceUrl)
                .build();
    }

    @Bean(name = "userWebClient")
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl(userMicroserviceUrl)
                .build();
    }

    @Bean(name = "paymentWebClient")
    public WebClient paymentWebClient(){
        return WebClient.builder()
                .baseUrl(paymentMicroserviceUrl)
                .build();
    }

    @Bean(name = "notificationWebClient")
    public WebClient notificationWebClient() {
        return WebClient.builder()
                .baseUrl(notificationMicroserviceUrl)
                .build();
    }

}
