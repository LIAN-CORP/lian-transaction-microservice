package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Client {

    private UUID id;
    private String name;
    private String phone;

}
