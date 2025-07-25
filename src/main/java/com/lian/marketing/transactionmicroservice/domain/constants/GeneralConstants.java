package com.lian.marketing.transactionmicroservice.domain.constants;

public class GeneralConstants {
    private GeneralConstants() {}

    public static final String COLOMBIA_PREFIX = "+57";
    public static final String PHONE_REGEX = "^\\+57[0-9]{9}$";
    public static final String USER_DO_NOT_EXISTS = "User do not exists";
    public static final String SAVING_TRANSACTION_SFL4J = "Saving transaction {}";
    public static final String TRANSACTION_SAVED_SFL4J = "Transaction saved with id {}";
    public static final String TYPE_MOVEMENT_NOT_VALID = "Type movement '%s' not valid";
    public static final String CLIENT_PHONE_IS_NOT_VALID = "Client phone '%s' is not valid";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
}
