package com.lian.marketing.transactionmicroservice.domain.constants;

public enum DebtReportEnum {

    ID("ID"),
    CLIENT("CLIENTE"),
    TOTAL_AMOUNT("TOTAL DEUDA"),
    TOTAL_PAID("TOTAL RESTANTE"),
    STATUS("ESTADO"),
    CREATED_AT("FECHA CREADO"),
    UPDATED_AT("FECHA ULTIMO PAGO");

    private String columnName;

    private DebtReportEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
