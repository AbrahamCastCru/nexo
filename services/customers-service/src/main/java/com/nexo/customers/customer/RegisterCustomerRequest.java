package com.nexo.customers.customer;

import java.time.LocalDate;

public record RegisterCustomerRequest(String email, String phone, String firstName, String lastName, String curp, LocalDate birthDate) {}
