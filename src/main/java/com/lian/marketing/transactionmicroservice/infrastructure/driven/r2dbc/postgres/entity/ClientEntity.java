package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Client")
public class ClientEntity {

    @Id
    private UUID id;
    private String name;
    private String phone;

}
