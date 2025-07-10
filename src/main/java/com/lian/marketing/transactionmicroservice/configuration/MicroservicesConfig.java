package com.lian.marketing.transactionmicroservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "microservice.url")
@Getter @Setter
public class MicroservicesConfig {
    private String user;
    private String stock;
    private String payment;
    private String notification;
}
