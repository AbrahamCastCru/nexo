package com.nexo.customers.customer;

import java.time.Instant;
import java.util.UUID;

public record CustomerResponse(UUID id, String email, String phone, String firstName, String lastName, boolean emailVerified, boolean phoneVerified, IdentityStatus identityStatus, Instant createdAt) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
            customer.getId(), 
            customer.getEmail(), 
            customer.getPhone(), 
            customer.getFirstName(), 
            customer.getLastName(), 
            customer.isEmailVerified(), 
            customer.isPhoneVerified(), 
            customer.getIdentityStatus(), 
            customer.getCreatedAt()
        );
    }
}
