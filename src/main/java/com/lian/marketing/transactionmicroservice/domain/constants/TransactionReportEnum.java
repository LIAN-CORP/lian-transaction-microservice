package com.lian.marketing.transactionmicroservice.domain.constants;

public enum TransactionReportEnum {

    ID("ID"),
    TYPE_MOVEMENT("TIPO DE MOVIMIENTO"),
    DATE("FECHA"),
    CLIENT("CLIENTE"),
    CLIENT_PHONE("CELULAR CLIENTE"),
    PRODUCT("PRODUCTO"),
    UNIT_PRICE("PRECIO UNIDAD"),
    QUANTITY("CANTIDAD"),
    TOTAL_PRICE("TOTAL");

    private String columnName;

    private TransactionReportEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

}
