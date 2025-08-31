package com.lian.marketing.transactionmicroservice.domain.constants;

public enum DetailTransactionReportEnum {

    ID("ID"),
    UNIT_PRICE("PRECIO UNIDAD"),
    QUANTITY("CANTIDAD"),
    TOTAL_PRICE("TOTAL"),
    TRANSACTION_ID("TRANSACCION"),
    PRODUCT("PRODUCTO");

    private String columnName;

    DetailTransactionReportEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

}
