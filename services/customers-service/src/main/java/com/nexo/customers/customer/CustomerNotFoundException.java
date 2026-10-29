package com.nexo.customers.customer;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(UUID id) {
        super("customer with id " + id + " was not found.");
    }
}
