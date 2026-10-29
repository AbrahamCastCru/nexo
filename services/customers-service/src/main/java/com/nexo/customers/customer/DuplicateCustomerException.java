package com.nexo.customers.customer;

public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException(String field) {
        super("Customer with this "+field+" already exists.");
    }
}
