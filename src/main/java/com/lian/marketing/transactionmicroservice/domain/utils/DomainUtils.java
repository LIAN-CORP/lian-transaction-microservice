package com.lian.marketing.transactionmicroservice.domain.utils;

import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.exception.ClientPhoneNumberIsNotValid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainUtils {
    private DomainUtils() {}

    public static String transformPhoneNumber(String phoneNumber) {
        String phone = phoneNumber;
        String phoneWithoutPrefix = phone.startsWith(GeneralConstants.COLOMBIA_PREFIX) ? phone.substring(GeneralConstants.COLOMBIA_PREFIX.length()) : phone;

        if(phoneWithoutPrefix.length() != 10){
            throw new ClientPhoneNumberIsNotValid(String.format("Phone number %s is not valid", phone));
        }

        if(!phone.startsWith(GeneralConstants.COLOMBIA_PREFIX)){
            log.info("Phone number has no prefix, adding prefix to +57 to {}", phone);
            phone = GeneralConstants.COLOMBIA_PREFIX + phoneWithoutPrefix;
        }

        return phone;
    }
}
