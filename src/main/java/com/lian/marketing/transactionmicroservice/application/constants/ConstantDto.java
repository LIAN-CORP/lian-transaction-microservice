package com.lian.marketing.transactionmicroservice.application.constants;

public class ConstantDto {
    private ConstantDto() {}

    public static final String CLIENT_NAME_MUST_BE_NOT_EMPTY = "Client name must be not empty";

    public static final String CLIENT_PHONE_MUST_BE_NOT_EMPTY = "Client phone must be not empty";
    public static final int MAX_CLIENT_PHONE_LENGTH = 10;
    public static final int MIN_CLIENT_PHONE_LENGTH = 10;
    public static final String CLIENT_PHONE_MESSAGE = "Phone must be 10 digits max";
    public static final String CLIENT_PHONE_REGEX = "^\\[0-9]{10}$";
}
