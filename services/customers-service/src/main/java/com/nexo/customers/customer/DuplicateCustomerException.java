package com.nexo.customers.customer;

public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException(String field) {
        super("customer with this "+field+" already exist.");
    }
}
