package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Table("detail_transaction")
public class DetailTransactionEntity {
    @Id
    private UUID id;
    @Column("unit_price")
    private Integer unitPrice;
    @Column("quantity")
    private Integer quantity;
    @Column("transaction_id")
    private UUID transactionId;
    @Column("product_id")
    private UUID productId;
}
