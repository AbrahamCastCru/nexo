package com.nexo.customers.customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
