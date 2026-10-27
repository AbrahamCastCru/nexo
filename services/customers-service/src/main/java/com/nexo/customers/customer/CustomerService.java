package com.nexo.customers.customer;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional 
    public Customer register(RegisterCustomerRequest customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.email().toLowerCase())) {
            throw new DuplicateCustomerException("email");
        }

        if (customerRepository.existsByPhone(customerRequest.phone())) {
            throw new DuplicateCustomerException("phone");
        }

        Customer newCustomer = new Customer(
            customerRequest.email(), 
            customerRequest.phone(), 
            customerRequest.firstName(), 
            customerRequest.lastName(), 
            customerRequest.curp(),
            customerRequest.birthDate()
        );

        return customerRepository.save(newCustomer);
    }
    
    @Transactional(readOnly = true)
    public Customer get(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Transactional 
    public Customer update(UUID id, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = this.get(id);

        if(updateCustomerRequest.phone() != null && !updateCustomerRequest.phone().equals(customer.getPhone())) {

            if(customerRepository.existsByPhone(updateCustomerRequest.phone())) {
                throw new DuplicateCustomerException("phone");
            }

            customer.changePhone(updateCustomerRequest.phone());
        }

        customer.changeName(
            (updateCustomerRequest.firstName() != null) ? updateCustomerRequest.firstName() : customer.getFirstName(),
            (updateCustomerRequest.lastName() != null ? updateCustomerRequest.lastName(): customer.getLastName())
        );

        return customerRepository.save(customer);
    }
}
