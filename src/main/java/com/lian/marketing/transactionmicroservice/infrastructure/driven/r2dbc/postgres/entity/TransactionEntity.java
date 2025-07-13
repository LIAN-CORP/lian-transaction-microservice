package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private UUID id;
    @Column("type_movement")
    private String typeMovement;
    @Column("transaction_date")
    private LocalDate transactionDate;
    @Column("user_id")
    private UUID userId;
    @Column("client_id")
    private UUID clientId;
}
