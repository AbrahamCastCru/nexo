package com.nexo.customers.customer;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository 
class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<UUID, Customer> customers = new ConcurrentHashMap<>(); 

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
       return Optional.ofNullable(customers.get(id));
    }

    @Override
    public boolean existsByEmail(String email) {
        return customers.values().stream()
            .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public boolean existsByPhone(String phone) {
        return customers.values().stream()
            .anyMatch(customer -> customer.getPhone().equals(phone));
    }

}
