package com.nexo.customers.customer;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(
    @Pattern(regexp = "^[0-9]{10}$", message = "Must be 10 digits") String phone, 
    @Size(min = 1, max = 100) String firstName, 
    @Size(min = 1, max = 100) String lastName
) {

    @AssertTrue(message = "At least one field must be provided")
    boolean isAnyFieldPresent(){
        return (this.phone != null || this.firstName != null || this.lastName != null ? true : false);
    }
}
