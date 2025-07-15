package com.lian.marketing.transactionmicroservice.application.constants;

public class ConstantDto {
    private ConstantDto() {}

    public static final String CLIENT_NAME_MUST_BE_NOT_EMPTY = "Client name must be not empty";

    public static final String CLIENT_PHONE_MUST_BE_NOT_EMPTY = "Client phone must be not empty";
    public static final int MAX_CLIENT_PHONE_LENGTH = 10;
    public static final int MIN_CLIENT_PHONE_LENGTH = 10;
    public static final String CLIENT_PHONE_MESSAGE = "Phone must be 10 digits max";
    public static final String CLIENT_PHONE_REGEX = "^\\+?\\d+$";

    // TRANSACTION
    public static final String TRANSACTION_TYPE_MOVEMENT_MUST_BE_NOT_EMPTY = "Transaction type movement must be not empty";
    public static final String USER_ID_MUST_BE_NOT_EMPTY = "User id must be not empty";

    //Products
    public static final String PRODUCT_LIST_MUST_BE_NOT_EMPTY = "Product list must be not empty";
    public static final String PRODUCT_ID_MUST_BE_NOT_EMPTY = "Product id must be not empty";
    public static final String PRODUCT_QUANTITY_IS_NOT_VALID = "Product quantity is not valid";
}
