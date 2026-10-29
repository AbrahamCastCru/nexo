package com.nexo.customers.customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody @Valid RegisterCustomerRequest registerCustomerRequest) {
        Customer newCustomer = customerService.register(registerCustomerRequest);
        CustomerResponse response = CustomerResponse.from(newCustomer);
        URI location = URI.create("/customers/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable UUID id) {
        return CustomerResponse.from(customerService.get(id));
    }

    @PatchMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable UUID id, @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        return CustomerResponse.from(customerService.update(id, updateCustomerRequest));
    }

}
