package com.nexo.customers.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException(String field) {
        super("Customer with this "+field+" already exist.");
    }
}
