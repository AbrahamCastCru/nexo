package com.nexo.customers.customer;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterCustomerRequest(
    @NotBlank @Email @Size(max = 254) String email, 
    @NotNull @Pattern(regexp = "^[0-9]{10}$", message = "Must be 10 digits") String phone,
    @NotBlank @Size(max = 100) String firstName, 
    @NotBlank @Size(max = 100) String lastName, 
    @NotNull @Pattern(regexp = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]$", message = "Must be 18 characters") String curp,
    @NotNull @Past LocalDate birthDate
) {}
